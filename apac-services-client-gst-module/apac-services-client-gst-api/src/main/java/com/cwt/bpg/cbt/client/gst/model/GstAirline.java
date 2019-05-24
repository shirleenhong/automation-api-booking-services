package com.cwt.bpg.cbt.client.gst.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(value = "gstAirlines", noClassnameStored = true)
@Indexes(@Index(fields = @Field("code")))
public class GstAirline implements Serializable {

    private static final long serialVersionUID = -553913445117107383L;

    @Id
    @NotEmpty
    private String code;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
