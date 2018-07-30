package com.cwt.bpg.cbt.exchange.order.model;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;

public class IndiaMiscInfo implements Serializable {

    private static final long serialVersionUID = 4914530217842938604L;

    private String filedFareNumber;

    private BigDecimal referenceFare;

    private BigDecimal lowFare;

    private String finalDestination;

    private String classOfServices;

    private String lowFareCarrier;

    @Max(value = 2)
    private Timezone timezone;

    private String bookingAction;

    private String realisedSavingsCode;

    private String missedSavingsCode;

    private String declinedAirline1;

    private BigDecimal declinedFare1;

    private String declinedAirline2;

    private BigDecimal declinedFare2;

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

    public String getDeclinedAirline1() {
        return declinedAirline1;
    }

    public void setDeclinedAirline1(String declinedAirline1) {
        this.declinedAirline1 = declinedAirline1;
    }

    public BigDecimal getDeclinedFare1() {
        return declinedFare1;
    }

    public void setDeclinedFare1(BigDecimal declinedFare1) {
        this.declinedFare1 = declinedFare1;
    }

    public String getDeclinedAirline2() {
        return declinedAirline2;
    }

    public void setDeclinedAirline2(String declinedAirline2) {
        this.declinedAirline2 = declinedAirline2;
    }

    public BigDecimal getDeclinedFare2() {
        return declinedFare2;
    }

    public void setDeclinedFare2(BigDecimal declinedFare2) {
        this.declinedFare2 = declinedFare2;
    }
}
