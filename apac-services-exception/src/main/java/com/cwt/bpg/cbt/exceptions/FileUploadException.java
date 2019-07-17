package com.cwt.bpg.cbt.exceptions;

import java.io.Serializable;
import java.util.List;

public class FileUploadException extends ServiceException {

    private static final long serialVersionUID = -346003903266039075L;

    private List<? extends Serializable> errors;

    public FileUploadException(String message, List<? extends Serializable> errors) {
        super(400, message);
        this.errors = errors;
    }

    public List<? extends Serializable> getErrors() {
        return errors;
    }
}
