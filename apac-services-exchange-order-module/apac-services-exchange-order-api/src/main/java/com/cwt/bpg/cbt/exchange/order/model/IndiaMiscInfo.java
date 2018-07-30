package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class IndiaMiscInfo implements Serializable {

    private static final long serialVersionUID = 4914530217842938604L;

    private String declinedAirline1;

    private BigDecimal declinedFare1;

    private String declinedAirline2;

    private BigDecimal declinedFare2;

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
