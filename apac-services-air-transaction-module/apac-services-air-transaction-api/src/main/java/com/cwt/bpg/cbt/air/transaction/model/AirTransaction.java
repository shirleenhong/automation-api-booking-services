package com.cwt.bpg.cbt.air.transaction.model;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.air.transaction.model.converter.PassthroughTypeConverter;
import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "airTransactions", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
@Converters(value = PassthroughTypeConverter.class)
public class AirTransaction implements Serializable
{

    private static final long serialVersionUID = 5943172847055937530L;

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @ApiModelProperty(hidden = true)
    private ObjectId id;

    private String airlineDescription;

    private String ccVendorName;

    private String ccType;

    private PassthroughType passthroughType;

    private List<String> bookingClasses;

    private String airlineCode;

    private String ccVendorCode;

    private String countryCode;

    private String clientAccountNumber;

    public AirTransaction()
    {
    }

    public AirTransaction(AirTransaction airTransaction)
    {
        this.id = airTransaction.id;
        this.airlineDescription = airTransaction.airlineDescription;
        this.ccVendorName = airTransaction.ccVendorName;
        this.ccType = airTransaction.ccType;
        this.passthroughType = airTransaction.passthroughType;
        this.bookingClasses = airTransaction.bookingClasses;
        this.airlineCode = airTransaction.airlineCode;
        this.ccVendorCode = airTransaction.ccVendorCode;
        this.countryCode = airTransaction.countryCode;
        this.clientAccountNumber = airTransaction.clientAccountNumber;
    }

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
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

    public String getClientAccountNumber()
    {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber)
    {
        this.clientAccountNumber = clientAccountNumber;
    }

    public String getAirlineDescription()
    {
        return airlineDescription;
    }

    public void setAirlineDescription(String airlineDescription)
    {
        this.airlineDescription = airlineDescription;
    }

    public String getCcVendorName()
    {
        return ccVendorName;
    }

    public void setCcVendorName(String ccVendorName)
    {
        this.ccVendorName = ccVendorName;
    }

    public String getCcType()
    {
        return ccType;
    }

    public void setCcType(String ccType)
    {
        this.ccType = ccType;
    }

    public PassthroughType getPassthroughType()
    {
        return passthroughType;
    }

    public void setPassthroughType(PassthroughType passthroughType)
    {
        this.passthroughType = passthroughType;
    }

    public List<String> getBookingClasses()
    {
        return bookingClasses;
    }

    public void setBookingClasses(List<String> bookingClasses)
    {
        this.bookingClasses = bookingClasses;
    }
}
