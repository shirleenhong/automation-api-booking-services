package com.cwt.bpg.cbt.exchange.order.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.math.BigDecimal;

@Entity(value = "clientTransactionFees", noClassnameStored = true)
@Indexes(@Index(fields = @Field("clientAccountNumber")))
public class FlatTransactionFee
{
    private String clientAccountNumber;
    private BigDecimal intOfflineAmount;
    private BigDecimal domOfflineAmount;
    private BigDecimal lccOfflineAmount;

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

    public String getClientAccountNumber()
    {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber)
    {
        this.clientAccountNumber = clientAccountNumber;
    }
}
