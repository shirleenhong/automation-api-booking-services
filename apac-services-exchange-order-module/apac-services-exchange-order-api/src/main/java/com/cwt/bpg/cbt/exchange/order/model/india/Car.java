package com.cwt.bpg.cbt.exchange.order.model.india;

import java.math.BigDecimal;
import java.time.Instant;

public class Car {

	private Instant pickupDateTime;

	private String pickupLocation;

	private String pickupDescription;

	private String company;

	private String name;

	private Instant dropOffDateTime;

	private String dropOffLocation;

	private String dropOffDescription;

	private String dailyRateCurrency;

	private BigDecimal dailyRateAmount;

	private String confirmationNumber;

	private String type;

	private Guarantor guarantyBy;

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

	public Guarantor getGuarantyBy() {
		return guarantyBy;
	}

	public void setGuarantyBy(Guarantor guarantyBy) {
		this.guarantyBy = guarantyBy;
	}

}
