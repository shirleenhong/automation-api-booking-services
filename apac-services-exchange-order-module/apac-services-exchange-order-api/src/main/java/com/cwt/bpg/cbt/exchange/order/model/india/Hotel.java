package com.cwt.bpg.cbt.exchange.order.model.india;

import java.math.BigDecimal;

public class Hotel {

	private String address;
	
	private String cityName;
	
	private String stateProvince;
	
	private String postCode;
	
	private String phoneNumber;
	
	private String dailyRateCurrency;
	
	private BigDecimal dailyRateAmount;
	
	private String confirmationNumber;

	private Guarantor guarantor;

	private String roomtype;

	private int noOfPeople;

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

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
}
