import sys, Ice
import BankService
from time import sleep

with Ice.initialize(sys.argv) as communicator:
    base = communicator.stringToProxy("pko:default -p 9001")
    bank = BankService.BankPrx.checkedCast(base)
    if not bank:
        raise RuntimeError("Invalid proxy")

    password = bank.newAccount("Tomasz", "Zdybel", "12345678901", 25000)
    print(password)
    account = BankService.AccountPrx.checkedCast(communicator.stringToProxy("12345678901Premium:default -p 9001"))
    print(account.getBalance(), account.getAccountType(), account.getIncome(), sep="\n")
    ctx = {
        "PESEL": "12345678901",
        "password": password
    }
    bank.depositMoney(12000, ctx)
    print(account.getBalance())
    bank.withdrawMoney(3000, ctx)
    print(account.getBalance())
    resp = bank.askForLoan("USD", 10000, 10, ctx)
    print(resp)
    sleep(5)
    resp = bank.askForLoan("USD", 10000, 10, ctx)
    print(resp)
