package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import io.swagger.annotations.ApiModelProperty;


@Entity(value = "clientTransactionFees", noClassnameStored = true)
@Indexes(@Index(fields = @Field("clientAccountNumber")))
public class FlatTransactionFee implements Serializable
{
    private static final long serialVersionUID = -7490304834086788667L;

    @Id
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String id;

    private String clientAccountNumber;

    private BigDecimal intOfflineAmount;
    private BigDecimal domOfflineAmount;
    private BigDecimal lccOfflineAmount;

    public String getClientAccountNumber()
    {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber)
    {
        this.clientAccountNumber = clientAccountNumber;
    }

    public BigDecimal getIntOfflineAmount()
    {
        return intOfflineAmount;
    }

    public void setIntOfflineAmount(BigDecimal intOfflineAmount)
    {
        this.intOfflineAmount = intOfflineAmount;
    }

    public BigDecimal getDomOfflineAmount()
    {
        return domOfflineAmount;
    }

    public void setDomOfflineAmount(BigDecimal domOfflineAmount)
    {
        this.domOfflineAmount = domOfflineAmount;
    }

    public BigDecimal getLccOfflineAmount()
    {
        return lccOfflineAmount;
    }

    public void setLccOfflineAmount(BigDecimal lccOfflineAmount)
    {
        this.lccOfflineAmount = lccOfflineAmount;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
