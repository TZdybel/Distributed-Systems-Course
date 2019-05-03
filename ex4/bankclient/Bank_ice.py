# -*- coding: utf-8 -*-
#
# Copyright (c) ZeroC, Inc. All rights reserved.
#
#
# Ice version 3.7.2
#
# <auto-generated>
#
# Generated from file `Bank.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module BankService
_M_BankService = Ice.openModule('BankService')
__name__ = 'BankService'

if 'AccountType' not in _M_BankService.__dict__:
    _M_BankService.AccountType = Ice.createTempClass()
    class AccountType(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    AccountType.Premium = AccountType("Premium", 0)
    AccountType.Standard = AccountType("Standard", 1)
    AccountType._enumerators = { 0:AccountType.Premium, 1:AccountType.Standard }

    _M_BankService._t_AccountType = IcePy.defineEnum('::BankService::AccountType', AccountType, (), AccountType._enumerators)

    _M_BankService.AccountType = AccountType
    del AccountType

if 'AccountDetails' not in _M_BankService.__dict__:
    _M_BankService.AccountDetails = Ice.createTempClass()
    class AccountDetails(object):
        def __init__(self, income=0.0, balance=0.0, type=_M_BankService.AccountType.Premium):
            self.income = income
            self.balance = balance
            self.type = type

        def __eq__(self, other):
            if other is None:
                return False
            elif not isinstance(other, _M_BankService.AccountDetails):
                return NotImplemented
            else:
                if self.income != other.income:
                    return False
                if self.balance != other.balance:
                    return False
                if self.type != other.type:
                    return False
                return True

        def __ne__(self, other):
            return not self.__eq__(other)

        def __str__(self):
            return IcePy.stringify(self, _M_BankService._t_AccountDetails)

        __repr__ = __str__

    _M_BankService._t_AccountDetails = IcePy.defineStruct('::BankService::AccountDetails', AccountDetails, (), (
        ('income', (), IcePy._t_double),
        ('balance', (), IcePy._t_double),
        ('type', (), _M_BankService._t_AccountType)
    ))

    _M_BankService.AccountDetails = AccountDetails
    del AccountDetails

if 'Client' not in _M_BankService.__dict__:
    _M_BankService.Client = Ice.createTempClass()
    class Client(object):
        def __init__(self, firstName='', lastName='', pesel='', accountDetails=Ice._struct_marker):
            self.firstName = firstName
            self.lastName = lastName
            self.pesel = pesel
            if accountDetails is Ice._struct_marker:
                self.accountDetails = _M_BankService.AccountDetails()
            else:
                self.accountDetails = accountDetails

        def __eq__(self, other):
            if other is None:
                return False
            elif not isinstance(other, _M_BankService.Client):
                return NotImplemented
            else:
                if self.firstName != other.firstName:
                    return False
                if self.lastName != other.lastName:
                    return False
                if self.pesel != other.pesel:
                    return False
                if self.accountDetails != other.accountDetails:
                    return False
                return True

        def __ne__(self, other):
            return not self.__eq__(other)

        def __str__(self):
            return IcePy.stringify(self, _M_BankService._t_Client)

        __repr__ = __str__

    _M_BankService._t_Client = IcePy.defineStruct('::BankService::Client', Client, (), (
        ('firstName', (), IcePy._t_string),
        ('lastName', (), IcePy._t_string),
        ('pesel', (), IcePy._t_string),
        ('accountDetails', (), _M_BankService._t_AccountDetails)
    ))

    _M_BankService.Client = Client
    del Client

if 'LoanDetails' not in _M_BankService.__dict__:
    _M_BankService.LoanDetails = Ice.createTempClass()
    class LoanDetails(object):
        def __init__(self, nativeCurrency='', valueOfNativeCurrency=0.0, foreignCurrency='', valueOfForeignCurrency=0.0):
            self.nativeCurrency = nativeCurrency
            self.valueOfNativeCurrency = valueOfNativeCurrency
            self.foreignCurrency = foreignCurrency
            self.valueOfForeignCurrency = valueOfForeignCurrency

        def __eq__(self, other):
            if other is None:
                return False
            elif not isinstance(other, _M_BankService.LoanDetails):
                return NotImplemented
            else:
                if self.nativeCurrency != other.nativeCurrency:
                    return False
                if self.valueOfNativeCurrency != other.valueOfNativeCurrency:
                    return False
                if self.foreignCurrency != other.foreignCurrency:
                    return False
                if self.valueOfForeignCurrency != other.valueOfForeignCurrency:
                    return False
                return True

        def __ne__(self, other):
            return not self.__eq__(other)

        def __str__(self):
            return IcePy.stringify(self, _M_BankService._t_LoanDetails)

        __repr__ = __str__

    _M_BankService._t_LoanDetails = IcePy.defineStruct('::BankService::LoanDetails', LoanDetails, (), (
        ('nativeCurrency', (), IcePy._t_string),
        ('valueOfNativeCurrency', (), IcePy._t_double),
        ('foreignCurrency', (), IcePy._t_string),
        ('valueOfForeignCurrency', (), IcePy._t_double)
    ))

    _M_BankService.LoanDetails = LoanDetails
    del LoanDetails

_M_BankService._t_Account = IcePy.defineValue('::BankService::Account', Ice.Value, -1, (), False, True, None, ())

if 'AccountPrx' not in _M_BankService.__dict__:
    _M_BankService.AccountPrx = Ice.createTempClass()
    class AccountPrx(Ice.ObjectPrx):

        def getAccountDetails(self, context=None):
            return _M_BankService.Account._op_getAccountDetails.invoke(self, ((), context))

        def getAccountDetailsAsync(self, context=None):
            return _M_BankService.Account._op_getAccountDetails.invokeAsync(self, ((), context))

        def begin_getAccountDetails(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Account._op_getAccountDetails.begin(self, ((), _response, _ex, _sent, context))

        def end_getAccountDetails(self, _r):
            return _M_BankService.Account._op_getAccountDetails.end(self, _r)

        def getPassword(self, context=None):
            return _M_BankService.Account._op_getPassword.invoke(self, ((), context))

        def getPasswordAsync(self, context=None):
            return _M_BankService.Account._op_getPassword.invokeAsync(self, ((), context))

        def begin_getPassword(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Account._op_getPassword.begin(self, ((), _response, _ex, _sent, context))

        def end_getPassword(self, _r):
            return _M_BankService.Account._op_getPassword.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_BankService.AccountPrx.ice_checkedCast(proxy, '::BankService::Account', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_BankService.AccountPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::BankService::Account'
    _M_BankService._t_AccountPrx = IcePy.defineProxy('::BankService::Account', AccountPrx)

    _M_BankService.AccountPrx = AccountPrx
    del AccountPrx

    _M_BankService.Account = Ice.createTempClass()
    class Account(Ice.Object):

        def ice_ids(self, current=None):
            return ('::BankService::Account', '::Ice::Object')

        def ice_id(self, current=None):
            return '::BankService::Account'

        @staticmethod
        def ice_staticId():
            return '::BankService::Account'

        def getAccountDetails(self, current=None):
            raise NotImplementedError("servant method 'getAccountDetails' not implemented")

        def getPassword(self, current=None):
            raise NotImplementedError("servant method 'getPassword' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_BankService._t_AccountDisp)

        __repr__ = __str__

    _M_BankService._t_AccountDisp = IcePy.defineClass('::BankService::Account', Account, (), None, ())
    Account._ice_type = _M_BankService._t_AccountDisp

    Account._op_getAccountDetails = IcePy.Operation('getAccountDetails', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_BankService._t_AccountDetails, False, 0), ())
    Account._op_getPassword = IcePy.Operation('getPassword', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())

    _M_BankService.Account = Account
    del Account

_M_BankService._t_Bank = IcePy.defineValue('::BankService::Bank', Ice.Value, -1, (), False, True, None, ())

if 'BankPrx' not in _M_BankService.__dict__:
    _M_BankService.BankPrx = Ice.createTempClass()
    class BankPrx(Ice.ObjectPrx):

        def newAccount(self, firstName, lastName, pesel, income, context=None):
            return _M_BankService.Bank._op_newAccount.invoke(self, ((firstName, lastName, pesel, income), context))

        def newAccountAsync(self, firstName, lastName, pesel, income, context=None):
            return _M_BankService.Bank._op_newAccount.invokeAsync(self, ((firstName, lastName, pesel, income), context))

        def begin_newAccount(self, firstName, lastName, pesel, income, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_newAccount.begin(self, ((firstName, lastName, pesel, income), _response, _ex, _sent, context))

        def end_newAccount(self, _r):
            return _M_BankService.Bank._op_newAccount.end(self, _r)

        def createPassword(self, context=None):
            return _M_BankService.Bank._op_createPassword.invoke(self, ((), context))

        def createPasswordAsync(self, context=None):
            return _M_BankService.Bank._op_createPassword.invokeAsync(self, ((), context))

        def begin_createPassword(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_createPassword.begin(self, ((), _response, _ex, _sent, context))

        def end_createPassword(self, _r):
            return _M_BankService.Bank._op_createPassword.end(self, _r)

        def getAccountName(self, context=None):
            return _M_BankService.Bank._op_getAccountName.invoke(self, ((), context))

        def getAccountNameAsync(self, context=None):
            return _M_BankService.Bank._op_getAccountName.invokeAsync(self, ((), context))

        def begin_getAccountName(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_getAccountName.begin(self, ((), _response, _ex, _sent, context))

        def end_getAccountName(self, _r):
            return _M_BankService.Bank._op_getAccountName.end(self, _r)

        def askForLoan(self, currency, amount, numOfMonths, context=None):
            return _M_BankService.Bank._op_askForLoan.invoke(self, ((currency, amount, numOfMonths), context))

        def askForLoanAsync(self, currency, amount, numOfMonths, context=None):
            return _M_BankService.Bank._op_askForLoan.invokeAsync(self, ((currency, amount, numOfMonths), context))

        def begin_askForLoan(self, currency, amount, numOfMonths, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_askForLoan.begin(self, ((currency, amount, numOfMonths), _response, _ex, _sent, context))

        def end_askForLoan(self, _r):
            return _M_BankService.Bank._op_askForLoan.end(self, _r)

        def depositMoney(self, amount, context=None):
            return _M_BankService.Bank._op_depositMoney.invoke(self, ((amount, ), context))

        def depositMoneyAsync(self, amount, context=None):
            return _M_BankService.Bank._op_depositMoney.invokeAsync(self, ((amount, ), context))

        def begin_depositMoney(self, amount, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_depositMoney.begin(self, ((amount, ), _response, _ex, _sent, context))

        def end_depositMoney(self, _r):
            return _M_BankService.Bank._op_depositMoney.end(self, _r)

        def withdrawMoney(self, amount, context=None):
            return _M_BankService.Bank._op_withdrawMoney.invoke(self, ((amount, ), context))

        def withdrawMoneyAsync(self, amount, context=None):
            return _M_BankService.Bank._op_withdrawMoney.invokeAsync(self, ((amount, ), context))

        def begin_withdrawMoney(self, amount, _response=None, _ex=None, _sent=None, context=None):
            return _M_BankService.Bank._op_withdrawMoney.begin(self, ((amount, ), _response, _ex, _sent, context))

        def end_withdrawMoney(self, _r):
            return _M_BankService.Bank._op_withdrawMoney.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_BankService.BankPrx.ice_checkedCast(proxy, '::BankService::Bank', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_BankService.BankPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::BankService::Bank'
    _M_BankService._t_BankPrx = IcePy.defineProxy('::BankService::Bank', BankPrx)

    _M_BankService.BankPrx = BankPrx
    del BankPrx

    _M_BankService.Bank = Ice.createTempClass()
    class Bank(Ice.Object):

        def ice_ids(self, current=None):
            return ('::BankService::Bank', '::Ice::Object')

        def ice_id(self, current=None):
            return '::BankService::Bank'

        @staticmethod
        def ice_staticId():
            return '::BankService::Bank'

        def newAccount(self, firstName, lastName, pesel, income, current=None):
            raise NotImplementedError("servant method 'newAccount' not implemented")

        def createPassword(self, current=None):
            raise NotImplementedError("servant method 'createPassword' not implemented")

        def getAccountName(self, current=None):
            raise NotImplementedError("servant method 'getAccountName' not implemented")

        def askForLoan(self, currency, amount, numOfMonths, current=None):
            raise NotImplementedError("servant method 'askForLoan' not implemented")

        def depositMoney(self, amount, current=None):
            raise NotImplementedError("servant method 'depositMoney' not implemented")

        def withdrawMoney(self, amount, current=None):
            raise NotImplementedError("servant method 'withdrawMoney' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_BankService._t_BankDisp)

        __repr__ = __str__

    _M_BankService._t_BankDisp = IcePy.defineClass('::BankService::Bank', Bank, (), None, ())
    Bank._ice_type = _M_BankService._t_BankDisp

    Bank._op_newAccount = IcePy.Operation('newAccount', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), IcePy._t_string, False, 0), ((), IcePy._t_string, False, 0), ((), IcePy._t_long, False, 0)), (), ((), IcePy._t_string, False, 0), ())
    Bank._op_createPassword = IcePy.Operation('createPassword', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())
    Bank._op_getAccountName = IcePy.Operation('getAccountName', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())
    Bank._op_askForLoan = IcePy.Operation('askForLoan', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), IcePy._t_double, False, 0), ((), IcePy._t_int, False, 0)), (), ((), _M_BankService._t_LoanDetails, False, 0), ())
    Bank._op_depositMoney = IcePy.Operation('depositMoney', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_double, False, 0),), (), None, ())
    Bank._op_withdrawMoney = IcePy.Operation('withdrawMoney', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_double, False, 0),), (), ((), IcePy._t_double, False, 0), ())

    _M_BankService.Bank = Bank
    del Bank

# End of module BankService
