package pl.edu.agh.sr.Bank.Implementation;

import com.zeroc.Ice.Current;
import pl.edu.agh.sr.generated.BankService.Account;
import pl.edu.agh.sr.generated.BankService.AccountDetails;
import pl.edu.agh.sr.generated.BankService.Client;

public class AccountI implements Account {
    private Client client;
    private String password;

    public AccountI(Client client, String password) {
        this.client = client;
        this.password = password;
    }

    @Override
    public AccountDetails getAccountDetails(Current current) {
//        System.out.println(current.ctx.get("PESEL") + " asked for details");
        return client.accountDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(double amount, Current current) {
        client.accountDetails.balance += amount;
    }
}
