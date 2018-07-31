package com.cwt.bpg.cbt.exchange.order.model.india;

public class Hotel {

	private String address;
	
	private String cityName;
	
	private String stateProvince;
	
	private String postCode;
	
	private String phoneNumber;
	
	private String dailyRateCurrency;
	
	private String dailyRateAmount;
	
	private String confirmationNumber;

	private Guarantor guarantyBy;

	private String hotelRoomType;

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

	public String getDailyRateAmount() {
		return dailyRateAmount;
	}

	public void setDailyRateAmount(String dailyRateAmount) {
		this.dailyRateAmount = dailyRateAmount;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public Guarantor getGuarantyBy() {
		return guarantyBy;
	}

	public void setGuarantyBy(Guarantor guarantyBy) {
		this.guarantyBy = guarantyBy;
	}

	public String getHotelRoomType() {
		return hotelRoomType;
	}

	public void setHotelRoomType(String hotelRoomType) {
		this.hotelRoomType = hotelRoomType;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	
	

}
