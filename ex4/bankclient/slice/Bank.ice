["java:package:pl.edu.agh.sr.generated"]
module BankService {
    enum AccountType { Premium, Standard };

    struct AccountDetails {
            double income;
            double balance;
            AccountType type;
    }

    struct Client {
        string firstName;
        string lastName;
        string pesel;
        AccountDetails accountDetails;
   }

    struct LoanDetails {
        string nativeCurrency;
        double valueOfNativeCurrency;
        string foreignCurrency;
        double valueOfForeignCurrency;
    }

    interface Account {
        AccountDetails getAccountDetails();
        string getPassword();
    }

    interface Bank {
        Account* newAccount(string firstName, string lastName, string pesel, long income);
        string createPassword();
        Account* getExistingAccount();
        LoanDetails askForLoan(string currency, double amount, int numOfMonths);
        void depositMoney(double amount);
        double withdrawMoney(double amount);
    }
}