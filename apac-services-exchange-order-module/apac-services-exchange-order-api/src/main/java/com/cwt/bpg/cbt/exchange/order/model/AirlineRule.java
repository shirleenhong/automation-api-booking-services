package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class AirlineRule implements Serializable {
    private static final long serialVersionUID = -3604480842389246624L;
    private String code;
	private boolean includeYqComm;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public boolean isIncludeYqComm()
    {
        return includeYqComm;
    }

    public void setIncludeYqComm(boolean includeYqComm)
    {
        this.includeYqComm = includeYqComm;
    }
}
