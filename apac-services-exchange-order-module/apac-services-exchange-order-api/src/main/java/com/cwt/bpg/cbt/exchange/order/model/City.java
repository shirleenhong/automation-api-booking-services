package com.cwt.bpg.cbt.exchange.order.model;

import org.mongodb.morphia.annotations.Entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(value = "cities", noClassnameStored = true)
public class City implements Serializable {

    @NotNull
    private String code;

    private String name;

    private String regionCode;

    private String countryCode;

    private String airportCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
