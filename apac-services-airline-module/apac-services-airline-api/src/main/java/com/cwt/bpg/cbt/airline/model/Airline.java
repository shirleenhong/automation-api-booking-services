package com.cwt.bpg.cbt.airline.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "airlines", noClassnameStored = true)
@Indexes(@Index(fields = @Field("iataCode")))
public class Airline implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	@Id
	@NotEmpty
    @ApiModelProperty(required = true)
    private String iataCode;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String name;

	private String ticketingCode;

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicketingCode() {
		return ticketingCode;
	}

	public void setTicketingCode(String ticketingCode) {
		this.ticketingCode = ticketingCode;
	}
}
