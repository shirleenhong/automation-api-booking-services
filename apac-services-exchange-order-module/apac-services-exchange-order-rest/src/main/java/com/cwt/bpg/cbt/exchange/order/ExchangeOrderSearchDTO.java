package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;

public class ExchangeOrderSearchDTO
{
    private String eoNumber;
    private String vendorCode;
    private String countryCode;
    private String raiseType;
    private String recordLocator;
    private String status;
    private Instant startCreationDate;
    private Instant endCreationDate;
    private String productCode;

    public String getEoNumber()
    {
        return eoNumber;
    }

    public void setEoNumber(String eoNumber)
    {
        this.eoNumber = eoNumber;
    }

    public String getVendorCode()
    {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode)
    {
        this.vendorCode = vendorCode;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getRaiseType()
    {
        return raiseType;
    }

    public void setRaiseType(String raiseType)
    {
        this.raiseType = raiseType;
    }

    public String getRecordLocator()
    {
        return recordLocator;
    }

    public void setRecordLocator(String recordLocator)
    {
        this.recordLocator = recordLocator;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Instant getStartCreationDate()
    {
        return startCreationDate;
    }

    public void setStartCreationDate(Instant startCreationDate)
    {
        this.startCreationDate = startCreationDate;
    }

    public Instant getEndCreationDate()
    {
        return endCreationDate;
    }

    public void setEndCreationDate(Instant endCreationDate)
    {
        this.endCreationDate = endCreationDate;
    }

    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

}
