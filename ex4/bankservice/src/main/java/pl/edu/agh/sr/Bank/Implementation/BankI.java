package pl.edu.agh.sr.Bank.Implementation;

import com.zeroc.Ice.AlreadyRegisteredException;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pl.edu.agh.sr.CurrenciesInquiry;
import pl.edu.agh.sr.CurrenciesResponse;
import pl.edu.agh.sr.Currency;
import pl.edu.agh.sr.ExchangeRatesServiceGrpc;
import pl.edu.agh.sr.generated.BankService.*;

import java.util.*;

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

        if (!availableCurrencies.isEmpty()) {
            Thread thread = new Thread(new UpdateExchangeRates());
            thread.start();
        }
    }

    @Override
    public AccountPrx newAccount(String firstName, String lastName, String pesel, long income, Current current) {
        if (pesel.matches("[0-9]+") && pesel.length() == 11) {
            if (accounts.get(pesel) != null) throw new AlreadyRegisteredException();
            AccountType type = income >= 20000 ? AccountType.Premium : AccountType.Standard;
            String password = createPassword(current);
            AccountI account = new AccountI(new Client(firstName, lastName, pesel, new AccountDetails(income, 0, type)), password);
            accounts.put(pesel, account);
            System.out.println("Account for " + pesel + " created");
            return AccountPrx.uncheckedCast(current.adapter.add(account, new Identity(pesel, type.toString())));
        } else return null;
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
        System.out.println("Inquiry about loan from " + current.ctx.get("PESEL"));
        AccountI account = getAccount(current);
        if (account != null && account.getAccountDetails(current).type.toString().equals("Premium")) {
            for (Currency curr : availableCurrencies.keySet()) {
                if (curr.toString().equals(currency)) {
                    double calculatedAmount = (Math.floor((amount * availableCurrencies.get(curr)) * 100))/100;
                    return new LoanDetails("PLN", calculatedAmount, curr.toString(), amount);
                }
            }
            return new LoanDetails("Bank does not operate on such currency", 0, "Bank does not operate on such currency", 0);
        }
        return new LoanDetails("Only for PREMIUM users", 0, "Only for PREMIUM users", 0);
    }

    @Override
    public AccountPrx getExistingAccount(Current current) {
        AccountI account = getAccount(current);
        if (account != null) {
            System.out.println(current.ctx.get("PESEL") + " logged in");
            return AccountPrx.uncheckedCast(current.adapter.createProxy(new Identity(current.ctx.get("PESEL"), account.getAccountDetails(current).type.toString())));
        } else return null;
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
        AccountI account = getAccount(current);
        if (account != null) {
            account.setBalance(amount, current);
            System.out.println(current.ctx.get("PESEL") + " made a deposit of " + amount);
        }
    }

    @Override
    public double withdrawMoney(double amount, Current current) {
        AccountI account = getAccount(current);
        if (account != null && account.getAccountDetails(current).balance >= amount) {
            account.setBalance(-amount, current);
            System.out.println(current.ctx.get("PESEL") + " made a withdraw of " + amount);
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
