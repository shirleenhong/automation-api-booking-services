package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public class IndiaInsuranceCustomFields implements Serializable {

	private static final long serialVersionUID = 2499491052481793076L;

	private String employeeNumber;
	
	private String employeeName;
	
	private String passportNumber;
	
	private String area;
	
	private String assigneeName;
	
	private String gender;
	
	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant dateOfBirth;
	
	private String address;
	
	private String street;
	
	private String city;
	
	private String pinCode;
	
	private String state;
	
	private String country;
	
	private String mobileNumber;
	
	private String maritalStatus;
	
	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant arrivalDate;
	
	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant departureDate;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Instant getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Instant arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Instant getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Instant departureDate) {
		this.departureDate = departureDate;
	}
}
