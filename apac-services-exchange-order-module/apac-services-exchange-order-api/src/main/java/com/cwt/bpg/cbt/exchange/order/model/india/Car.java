package com.cwt.bpg.cbt.exchange.order.model.india;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.Valid;

public class Car implements Serializable {

	private static final long serialVersionUID = -1953882582608518378L;

	private String segment;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant pickupDateTime;

	private String pickupLocation;

	private String pickupDescription;

	private String company;

	private String name;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant dropOffDateTime;

	private String dropOffLocation;

	private String dropOffDescription;

	private String dailyRateCurrency;

	private BigDecimal dailyRateAmount;

	private String confirmationNumber;

	private String type;
	
	@Valid
	private Guarantor guarantor;

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public Instant getPickupDateTime() {
		return pickupDateTime;
	}

	public void setPickupDateTime(Instant pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getPickupDescription() {
		return pickupDescription;
	}

	public void setPickupDescription(String pickupDescription) {
		this.pickupDescription = pickupDescription;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getDropOffDateTime() {
		return dropOffDateTime;
	}

	public void setDropOffDateTime(Instant dropOffDateTime) {
		this.dropOffDateTime = dropOffDateTime;
	}

	public String getDropOffLocation() {
		return dropOffLocation;
	}

	public void setDropOffLocation(String dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

	public String getDropOffDescription() {
		return dropOffDescription;
	}

	public void setDropOffDescription(String dropOffDescription) {
		this.dropOffDescription = dropOffDescription;
	}

	public String getDailyRateCurrency() {
		return dailyRateCurrency;
	}

	public void setDailyRateCurrency(String dailyRateCurrency) {
		this.dailyRateCurrency = dailyRateCurrency;
	}

	public BigDecimal getDailyRateAmount() {
		return dailyRateAmount;
	}

	public void setDailyRateAmount(BigDecimal dailyRateAmount) {
		this.dailyRateAmount = dailyRateAmount;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}
	
}
