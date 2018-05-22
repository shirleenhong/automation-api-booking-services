package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "airlineRules", noClassnameStored = true)
@Indexes(@Index(fields = @Field("code")))
public class AirlineRule implements Serializable {
	
    private static final long serialVersionUID = -3604480842389246624L;
    
    @Id
    @NotNull
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
