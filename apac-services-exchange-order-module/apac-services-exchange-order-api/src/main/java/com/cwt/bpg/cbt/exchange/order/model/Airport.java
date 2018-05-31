package com.cwt.bpg.cbt.exchange.order.model;

import org.mongodb.morphia.annotations.Entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(value = "airports", noClassnameStored = true)
public class Airport implements Serializable {

	private static final long serialVersionUID = 7858317666413989443L;

	@NotNull
    private String code;

    private String name;

    @NotNull
    private String cityCode;

    private String regionCode;

    @NotNull
    private String countryCode;

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

}
