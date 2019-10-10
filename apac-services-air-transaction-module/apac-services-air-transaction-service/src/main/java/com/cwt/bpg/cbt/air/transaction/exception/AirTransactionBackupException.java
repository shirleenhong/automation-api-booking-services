package com.cwt.bpg.cbt.air.transaction.exception;

import com.cwt.bpg.cbt.exceptions.ServiceException;

public class AirTransactionBackupException extends ServiceException
{

    private static final long serialVersionUID = -7499913675541436897L;

    public AirTransactionBackupException(String message, Throwable e)
    {
        super(500, message, e);
    }
}
