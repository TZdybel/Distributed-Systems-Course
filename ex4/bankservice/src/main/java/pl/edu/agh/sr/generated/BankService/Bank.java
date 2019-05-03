//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.2
//
// <auto-generated>
//
// Generated from file `Bank.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package pl.edu.agh.sr.generated.BankService;

public interface Bank extends com.zeroc.Ice.Object
{
    String newAccount(String firstName, String lastName, String pesel, long income, com.zeroc.Ice.Current current);

    String createPassword(com.zeroc.Ice.Current current);

    String getAccountName(com.zeroc.Ice.Current current);

    LoanDetails askForLoan(String currency, double amount, int numOfMonths, com.zeroc.Ice.Current current);

    void depositMoney(double amount, com.zeroc.Ice.Current current);

    double withdrawMoney(double amount, com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::BankService::Bank",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::BankService::Bank";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_newAccount(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_firstName;
        String iceP_lastName;
        String iceP_pesel;
        long iceP_income;
        iceP_firstName = istr.readString();
        iceP_lastName = istr.readString();
        iceP_pesel = istr.readString();
        iceP_income = istr.readLong();
        inS.endReadParams();
        String ret = obj.newAccount(iceP_firstName, iceP_lastName, iceP_pesel, iceP_income, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_createPassword(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        String ret = obj.createPassword(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getAccountName(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        String ret = obj.getAccountName(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_askForLoan(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_currency;
        double iceP_amount;
        int iceP_numOfMonths;
        iceP_currency = istr.readString();
        iceP_amount = istr.readDouble();
        iceP_numOfMonths = istr.readInt();
        inS.endReadParams();
        LoanDetails ret = obj.askForLoan(iceP_currency, iceP_amount, iceP_numOfMonths, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        LoanDetails.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_depositMoney(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        double iceP_amount;
        iceP_amount = istr.readDouble();
        inS.endReadParams();
        obj.depositMoney(iceP_amount, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_withdrawMoney(Bank obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        double iceP_amount;
        iceP_amount = istr.readDouble();
        inS.endReadParams();
        double ret = obj.withdrawMoney(iceP_amount, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeDouble(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "askForLoan",
        "createPassword",
        "depositMoney",
        "getAccountName",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "newAccount",
        "withdrawMoney"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_askForLoan(this, in, current);
            }
            case 1:
            {
                return _iceD_createPassword(this, in, current);
            }
            case 2:
            {
                return _iceD_depositMoney(this, in, current);
            }
            case 3:
            {
                return _iceD_getAccountName(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 8:
            {
                return _iceD_newAccount(this, in, current);
            }
            case 9:
            {
                return _iceD_withdrawMoney(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
