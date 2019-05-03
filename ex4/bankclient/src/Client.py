import sys, Ice

import BankService


def create_new_account(bank):
    print("Enter first name: ")
    first_name = input()
    print("Enter last name: ")
    last_name = input()
    print("Enter yout PESEL (it will be your 'username'): ")
    pesel = input()
    if not pesel.isnumeric() or len(pesel) != 11:
        print("Invalid PESEL")
        exit(1)
    print("What is your income?")
    try:
        income = float(input())
    except ValueError:
        print("NaN")
        exit(1)
    try:
        account = bank.newAccount(first_name, last_name, pesel, income)
    except Ice.UnknownLocalException:
        print("User with that PESEL already exists")
        return log_in(bank)
    password = account.getPassword()
    type = account.getAccountDetails().type
    print("Your password: " + password)
    print("Your account type: " + str(type))
    ctx = {
        "PESEL": pesel,
        "password": password
    }
    return ctx, account


def log_in(bank):
    print("Enter login (PESEL): ")
    pesel = input()
    if not pesel.isnumeric() or len(pesel) != 11:
        print("Invalid PESEL")
        exit(1)
    print("Enter password")
    password = input()
    ctx = {
        "PESEL": pesel,
        "password": password
    }
    account = bank.getExistingAccount(ctx)
    if account is None:
        print("Wrong password or no such user")
        exit(1)
    return ctx, account


def user_console(bank, ctx, account):
    while True:
        print("What you want to do?")
        print("1 - get account details", "2 - ask for loan", "3 - deposit money",
              "4 - withdraw money", "5 - change account", "6 - end session", sep="\n")
        option = input()
        if option == "1":
            details = account.getAccountDetails(ctx)
            print("Income - " + str(details.income), "Balance - " + str(details.balance), "Type - " + str(details.type), sep="\n")
        elif option == "2":
            print("What currency you want to use?")
            currency = input()
            print("What amount?")
            try:
                amount = float(input())
            except ValueError:
                print("NaN")
                continue
            print("For how many moths?")
            try:
                months = float(input())
            except ValueError:
                print("NaN")
                continue
            response = bank.askForLoan(currency, amount, months, ctx)
            print(response)
        elif option == "3":
            print("What amount?")
            try:
                amount = float(input())
            except ValueError:
                print("NaN")
                continue
            bank.depositMoney(amount, ctx)
            print("Done!")
        elif option == "4":
            print("What amount?")
            try:
                amount = float(input())
                response = bank.withdrawMoney(amount, ctx)
            except ValueError:
                print("NaN")
                continue
            if response == 0:
                print("Cannot withdraw that amount of cash")
            else:
                print("Withdraw " + str(response) + "PLN")
        elif option == "5":
            ctx, account = init(bank)
            print("Successfully changed account")
        elif option == "6":
            break
        else:
            print("Wrong option")


def init(bank):
    print("You want to create new account?")
    answer = input()
    if answer == 'yes':
        ctx, account = create_new_account(bank)
    elif answer == 'no':
        ctx, account = log_in(bank)
    else:
        print("Inappropriate input, shutting down...")
        exit(1)
    return ctx, account


def main():
    with Ice.initialize(sys.argv) as communicator:
        print("What bank you want to use?")
        bank_name = input()
        print("What port is bank server on?")
        try:
            port = int(input())
            base = communicator.stringToProxy(bank_name + ":default -p " + str(port))
            bank = BankService.BankPrx.checkedCast(base)
        except ValueError:
            print("NaN")
            exit(1)
        except (Ice.ConnectionRefusedException, Ice.ObjectNotExistException):
            print("Such bank does not exist")
            exit(1)
        if not bank:
            raise RuntimeError("Invalid proxy")

        ctx, account = init(bank)

        user_console(bank, ctx, account)


if __name__ == '__main__':
    main()
