package com.cwt.bpg.cbt.air.transaction.exception;

import com.cwt.bpg.cbt.exceptions.ServiceException;

public class AirTransactionUploadException extends ServiceException
{
    private static final long serialVersionUID = -2597566368368945234L;

    public AirTransactionUploadException(String message, Throwable e)
    {
        super(500, message, e);
    }
}
