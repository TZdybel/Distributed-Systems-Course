package pl.edu.agh.sr.Bank.Implementation;

import com.zeroc.Ice.Current;
import pl.edu.agh.sr.generated.BankService.Account;
import pl.edu.agh.sr.generated.BankService.AccountType;
import pl.edu.agh.sr.generated.BankService.Client;

public class AccountI implements Account {
    private Client client;
    private String password;

    public AccountI(Client client, String password) {
        this.client = client;
        this.password = password;
    }

    @Override
    public double getBalance(Current current) {
        return client.balance;
    }

    @Override
    public AccountType getAccountType(Current current) {
        return client.type;
    }

    @Override
    public double getIncome(Current current) {
        return client.income;
    }

    @Override
    public String getPassword(Current current) {
        return password;
    }

    @Override
    public void setBalance(double amount, Current current) {
        client.balance += amount;
    }
}
