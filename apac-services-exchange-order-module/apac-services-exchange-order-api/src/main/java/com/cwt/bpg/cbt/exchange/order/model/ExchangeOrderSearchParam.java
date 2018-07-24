package com.cwt.bpg.cbt.exchange.order.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public class ExchangeOrderSearchParam extends ExchangeOrder
{

    private static final long serialVersionUID = -2602841755135246343L;

    @NotNull
    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z", required = true)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant startCreationDate;

    @NotNull
    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z", required = true)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant endCreationDate;
    
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
    
}
