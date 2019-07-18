package com.cwt.bpg.cbt.client.gst.model;

import java.io.Serializable;

public class FieldError implements Serializable {

    private static final long serialVersionUID = -4252686960433266231L;

    private String field;
    private Object value;
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static FieldError create(String field, Object value, String message) {
        FieldError error = new FieldError();
        error.field = field;
        error.value = value;
        error.message = message;
        return error;
    }
}
