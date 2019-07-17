package com.cwt.bpg.cbt.client.gst.model;

import java.io.Serializable;
import java.util.List;

public class ValidationError implements Serializable {

    private static final long serialVersionUID = -8988987007275965029L;

    private int rowNumber;
    private List<FieldError> errors;

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public static ValidationError create(int rowNumber, List<FieldError> errors) {
        ValidationError error = new ValidationError();
        error.rowNumber = rowNumber;
        error.errors = errors;
        return error;
    }
}
