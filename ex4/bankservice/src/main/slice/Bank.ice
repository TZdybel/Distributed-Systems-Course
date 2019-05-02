["java:package:pl.edu.agh.sr.generated"]
module BankService {
    enum AccountType { Premium, Standard };

    struct Client {
        string firstName;
        string lastName;
        string pesel;
        double income;
        double balance;
        AccountType type;
    }

    struct LoanDetails {
        string nativeCurrency;
        double valueOfNativeCurrency;
        string foreignCurrency;
        double valueOfForeignCurrency;
    }

    interface Account {
        double getBalance();
        AccountType getAccountType();
        double getIncome();
        string getPassword();
        void setBalance(double amount);
    }

    interface Bank {
        string newAccount(string firstName, string lastName, string pesel, long income);
        string createPassword();
        LoanDetails askForLoan(string currency, double amount, int numOfMonths);
        void depositMoney(double amount);
        double withdrawMoney(double amount);
    }
}