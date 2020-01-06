package com.cwt.bpg.cbt.air.transaction.model;

import java.io.Serializable;
import java.util.List;

public class AirTransactionInput implements Serializable
{

    private static final long serialVersionUID = 5943172847055937530L;

    private String groupId;

    private List<String> bookingClasses;

    private String countryCode;

    private String airlineCode;

    private String ccVendorCode;

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public List<String> getBookingClasses()
    {
        return bookingClasses;
    }

    public void setBookingClasses(List<String> bookingClasses)
    {
        this.bookingClasses = bookingClasses;
    }

    public String getAirlineCode()
    {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode)
    {
        this.airlineCode = airlineCode;
    }

    public String getCcVendorCode()
    {
        return ccVendorCode;
    }

    public void setCcVendorCode(String ccVendorCode)
    {
        this.ccVendorCode = ccVendorCode;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }
}
