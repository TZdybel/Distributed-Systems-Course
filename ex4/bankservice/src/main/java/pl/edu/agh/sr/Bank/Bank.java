package pl.edu.agh.sr.Bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.SocketException;
import pl.edu.agh.sr.Bank.Implementation.BankI;
import pl.edu.agh.sr.Currency;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.zeroc.Ice.Util.stringToIdentity;
import static com.zeroc.Ice.Util.initialize;

public class Bank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Communicator communicator = initialize(args)) {
            System.out.println("How to name this bank?");
            String bankName = scanner.nextLine();
            System.out.println("What port to use?");
            int port = 0;
            ObjectAdapter adapter = null;
            try {
                port = Integer.parseInt(scanner.nextLine());
                adapter = communicator.createObjectAdapterWithEndpoints(bankName + "Adapter", "default -p " + port);
            } catch (NumberFormatException e) {
                System.out.println("NaN");
                System.exit(1);
            } catch (SocketException e) {
                System.out.println("Some bank already works under this port");
                System.exit(1);
            }
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
            BankI bank = new BankI(currencies);
            adapter.add(bank, stringToIdentity(bankName));
            adapter.activate();
            System.out.println(bankName + " created at localhost with port " + port);
            communicator.waitForShutdown();
        }
    }
}
