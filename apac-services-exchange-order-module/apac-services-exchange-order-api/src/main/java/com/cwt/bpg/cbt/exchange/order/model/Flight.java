package com.cwt.bpg.cbt.exchange.order.model;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;

public class Flight implements Serializable {

    private static final long serialVersionUID = -896536510709809066L;

    private String code;

    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant dateAndTime;

    private String fromLocation;

    private String fromDescription;

    private String fromContactNumber;

    private String toLocation;

    private String toDescription;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromDescription() {
        return fromDescription;
    }

    public void setFromDescription(String fromDescription) {
        this.fromDescription = fromDescription;
    }

    public String getFromContactNumber() {
        return fromContactNumber;
    }

    public void setFromContactNumber(String fromContactNumber) {
        this.fromContactNumber = fromContactNumber;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToDescription() {
        return toDescription;
    }

    public void setToDescription(String toDescription) {
        this.toDescription = toDescription;
    }
}
