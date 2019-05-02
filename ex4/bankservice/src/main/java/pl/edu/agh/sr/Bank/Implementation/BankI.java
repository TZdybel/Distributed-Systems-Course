package pl.edu.agh.sr.Bank.Implementation;

import com.zeroc.Ice.Current;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pl.edu.agh.sr.CurrenciesInquiry;
import pl.edu.agh.sr.CurrenciesResponse;
import pl.edu.agh.sr.Currency;
import pl.edu.agh.sr.ExchangeRatesServiceGrpc;
import pl.edu.agh.sr.generated.BankService.*;

import java.util.*;

import static com.zeroc.Ice.Util.stringToIdentity;

public class BankI implements Bank {
    private Map<String, AccountI> accounts = new HashMap<>();
    private Map<Currency, Double> availableCurrencies = new HashMap<>();
    private ManagedChannel channel;
    private ExchangeRatesServiceGrpc.ExchangeRatesServiceBlockingStub stub;

    public BankI(List<Currency> availableCurrencies) {
        for (Currency curr: availableCurrencies) {
            this.availableCurrencies.put(curr, 0.00);
        }

        channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        stub = ExchangeRatesServiceGrpc.newBlockingStub(channel);

        Thread thread = new Thread(new UpdateExchangeRates());
        thread.start();
    }

    @Override
    public String newAccount(String firstName, String lastName, String pesel, long income, Current current) {
        if (pesel.matches("[0-9]+") && pesel.length() == 11) {
            AccountType type = income >= 20000 ? AccountType.Premium : AccountType.Standard;
            String password = createPassword(current);
            AccountI account = new AccountI(new Client(firstName, lastName, pesel, income, 0, type), password);
            current.adapter.add(account, stringToIdentity(pesel + account.getAccountType(current).toString()));
            current.adapter.activate();
            accounts.put(pesel, account);
            return password;
        } else return "";
    }

    @Override
    public String createPassword(Current current) {
        String signs = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int length = 4;
        StringBuilder builder = new StringBuilder(4);
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(signs.charAt(rand.nextInt(signs.length())));
        }
        return builder.toString();
    }

    @Override
    public LoanDetails askForLoan(String currency, double amount, int numOfMonths, Current current) {
        for (Currency curr : availableCurrencies.keySet()) {
            if (curr.toString().equals(currency)) {
                return new LoanDetails("PLN", amount * availableCurrencies.get(curr), curr.toString(), amount);
            }
        } return null;
    }

    private AccountI getAccount(Current current) {
        String pesel = current.ctx.get("PESEL");
        String password = current.ctx.get("password");
        AccountI account = accounts.get(pesel);
        if (account != null && account.getPassword(current).equals(password)) {
            return account;
        }
        return null;
    }

    @Override
    public void depositMoney(double amount, Current current) {
        Account account = getAccount(current);
        if (account != null) {
            account.setBalance(amount, current);
        }
    }

    @Override
    public double withdrawMoney(double amount, Current current) {
        Account account = getAccount(current);
        if (account != null) {
            account.setBalance(-amount, current);
            return amount;
        }
        return 0;
    }

    private class UpdateExchangeRates implements Runnable {
        @Override
        public void run() {
            CurrenciesInquiry.Builder builder = CurrenciesInquiry.newBuilder();
            for (Currency curr: availableCurrencies.keySet()) {
                builder.addCurrencies(curr);
            }

            Iterator<CurrenciesResponse> currenciesResponse = stub.exchangeRates(builder.build());

            while(true) {
                List<CurrenciesResponse.CurrencyExchange> list = currenciesResponse.next().getCurrencyExchangesList();
                for (CurrenciesResponse.CurrencyExchange exchange: list) {
                    availableCurrencies.replace(exchange.getName(), exchange.getExchange());
                }
                System.out.println(availableCurrencies);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
