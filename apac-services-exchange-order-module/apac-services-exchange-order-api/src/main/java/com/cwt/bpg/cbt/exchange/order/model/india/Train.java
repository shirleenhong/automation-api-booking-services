package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

public class Train implements Serializable {

	private static final long serialVersionUID = 2737677039047402942L;

	private String number;

	private String name;

	private String cabinClass;

    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant dateTime;

	private String origin;

	private String originDesc;

	private String destination;

	private String destinationDesc;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}

	public String getOrigin()
	{
		return origin;
	}

	public void setOrigin(String origin)
	{
		this.origin = origin;
	}

	public String getOriginDesc()
	{
		return originDesc;
	}

	public void setOriginDesc(String originDesc)
	{
		this.originDesc = originDesc;
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public String getDestinationDesc()
	{
		return destinationDesc;
	}

	public void setDestinationDesc(String destinationDesc)
	{
		this.destinationDesc = destinationDesc;
	}

    public Instant getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Instant dateTime)
    {
        this.dateTime = dateTime;
    }
}
