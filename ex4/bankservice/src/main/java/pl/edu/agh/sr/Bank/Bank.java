package pl.edu.agh.sr.Bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import pl.edu.agh.sr.Bank.Implementation.BankI;
import pl.edu.agh.sr.Currency;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.zeroc.Ice.Util.stringToIdentity;
import static com.zeroc.Ice.Util.initialize;

public class Bank {
    public static void main(String[] args) {
        int port = 9001;
        Scanner scanner = new Scanner(System.in);
        try (Communicator communicator = initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("BankAdapter", "default -p " + port);
            List<Currency> currencies = new LinkedList<>();
            boolean end = false;
            while (!end) {
                System.out.println("What currencies bank will operate?");
                String currency = scanner.nextLine();
                if (currency.contains("end")) {
                    end = true;
                } else {
                    for (Currency curr : Currency.values()) {
                        if (curr.name().equals(currency)) {
                            currencies.add(curr);
                            System.out.println(currencies);
                        }
                    }
                }
            }
            System.out.println("How to name this bank?");
            String bankName = scanner.nextLine();
            BankI bank = new BankI(currencies);
            adapter.add(bank, stringToIdentity(bankName));
            adapter.activate();
            System.out.println(bankName + " created at localhost with port " + port);
            communicator.waitForShutdown();
        }
    }
}
