package com.cwt.bpg.cbt.exchange.order.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;

public class MiscInfo implements Serializable {

    private static final long serialVersionUID = -3387321962514898499L;

    private String filedFareNumber;

    private BigDecimal referenceFare;

    private BigDecimal lowFare;

    private String finalDestination;

    private String classOfServices;

    private String lowFareCarrier;

    @ApiModelProperty(allowableValues = "PT, ET")
    @Max(value = 2)
    private Timezone timezone;

    private String bookingAction;

    private String realisedSavingsCode;

    private String missedSavingsCode;

    public String getFiledFareNumber() {
        return filedFareNumber;
    }

    public void setFiledFareNumber(String filedFareNumber) {
        this.filedFareNumber = filedFareNumber;
    }

    public BigDecimal getReferenceFare() {
        return referenceFare;
    }

    public void setReferenceFare(BigDecimal referenceFare) {
        this.referenceFare = referenceFare;
    }

    public BigDecimal getLowFare() {
        return lowFare;
    }

    public void setLowFare(BigDecimal lowFare) {
        this.lowFare = lowFare;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getClassOfServices() {
        return classOfServices;
    }

    public void setClassOfServices(String classOfServices) {
        this.classOfServices = classOfServices;
    }

    public String getLowFareCarrier() {
        return lowFareCarrier;
    }

    public void setLowFareCarrier(String lowFareCarrier) {
        this.lowFareCarrier = lowFareCarrier;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public String getBookingAction() {
        return bookingAction;
    }

    public void setBookingAction(String bookingAction) {
        this.bookingAction = bookingAction;
    }

    public String getRealisedSavingsCode() {
        return realisedSavingsCode;
    }

    public void setRealisedSavingsCode(String realisedSavingsCode) {
        this.realisedSavingsCode = realisedSavingsCode;
    }

    public String getMissedSavingsCode() {
        return missedSavingsCode;
    }

    public void setMissedSavingsCode(String missedSavingsCode) {
        this.missedSavingsCode = missedSavingsCode;
    }
}
