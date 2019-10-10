package com.cwt.bpg.cbt.client.gst.exception;

import com.cwt.bpg.cbt.exceptions.ServiceException;

public class ClientGstInfoBackupException extends ServiceException {

    private static final long serialVersionUID = -19424237348483608L;

    public ClientGstInfoBackupException(String message, Throwable e) {
        super(500, message, e);
    }
}
