package com.cwt.bpg.cbt.client.gst.exception;

import com.cwt.bpg.cbt.exceptions.ServiceException;

public class ClientGstInfoUploadException extends ServiceException
{
    private static final long serialVersionUID = -19424237348483608L;

    public ClientGstInfoUploadException(String message, Throwable e)
    {
        super(500, message, e);
    }
}
