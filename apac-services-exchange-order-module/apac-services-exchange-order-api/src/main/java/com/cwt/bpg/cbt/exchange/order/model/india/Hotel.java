package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Hotel implements Serializable {

	private static final long serialVersionUID = 4676705321251464166L;

	private String name;

	private String address;

	private String cityName;

	private String stateProvince;

	private String postCode;

	private String phoneNumber;

	private String dailyRateCurrency;

	private BigDecimal dailyRateAmount;

	private String confirmationNumber;

	@Valid
	private Guarantor guarantor;

	private String roomType;

	private Integer noOfPeople;
	
	private String airSegment;
	
	private String hotelSegment;
	
	private String cityCode;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant checkInDateTime;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant checkOutDateTime; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(Integer noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public String getAirSegment() {
		return airSegment;
	}

	public void setAirSegment(String airSegment) {
		this.airSegment = airSegment;
	}

	public String getHotelSegment() {
		return hotelSegment;
	}

	public void setHotelSegment(String hotelSegment) {
		this.hotelSegment = hotelSegment;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Instant getCheckInDateTime() {
		return checkInDateTime;
	}

	public void setCheckInDateTime(Instant checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}

	public Instant getCheckOutDateTime() {
		return checkOutDateTime;
	}

	public void setCheckOutDateTime(Instant checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}
	
}
