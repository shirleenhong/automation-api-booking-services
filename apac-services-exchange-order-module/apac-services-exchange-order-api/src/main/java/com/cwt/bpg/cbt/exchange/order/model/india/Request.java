package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;
import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

public class Request implements Serializable {

	private static final long serialVersionUID = 3951172881169529734L;

	private String platingCarrier;

	private String fareType;

	@Valid
	private FormOfPayment formOfPayment;

	private VirtualMiscMultipurposeDoc vmpd;

	private ConsolInfo consolInfo;

	private String forexType;

	private String travelType;

	private String expectedDeliveryDate;

	private String deliveryAddress;

	private String passengerEmployeeId;

	private String businessUnit;

	private String passportNumber;

	private String contactNumber;

	private RequestAmount requestAmount;

	private CheckList docCheckList;

	private Hotel hotel;

	private String packageName;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant travelDate;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant dateTime;

	private String city;

	private String location;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant startDate;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant endDate;

	private String name;

	private String details;

	private String origin;

	private String originDesc;

	private String destination;

	private String destinationDesc;

	private Train train;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant applicationDate;

	private Car car;

	public String getPlatingCarrier() {
		return platingCarrier;
	}

	public void setPlatingCarrier(String platingCarrier) {
		this.platingCarrier = platingCarrier;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public FormOfPayment getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(FormOfPayment formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public VirtualMiscMultipurposeDoc getVmpd() {
		return vmpd;
	}

	public void setVmpd(VirtualMiscMultipurposeDoc vmpd) {
		this.vmpd = vmpd;
	}

	public ConsolInfo getConsolInfo() {
		return consolInfo;
	}

	public void setConsolInfo(ConsolInfo consolInfo) {
		this.consolInfo = consolInfo;
	}

	public String getForexType() {
		return forexType;
	}

	public void setForexType(String forexType) {
		this.forexType = forexType;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPassengerEmployeeId() {
		return passengerEmployeeId;
	}

	public void setPassengerEmployeeId(String passengerEmployeeId) {
		this.passengerEmployeeId = passengerEmployeeId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public RequestAmount getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(RequestAmount requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Instant getDateTime() {
		return dateTime;
	}

	public void setDateTime(Instant dateTime) {
		this.dateTime = dateTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginDesc() {
		return originDesc;
	}

	public void setOriginDesc(String originDesc) {
		this.originDesc = originDesc;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationDesc() {
		return destinationDesc;
	}

	public void setDestinationDesc(String destinationDesc) {
		this.destinationDesc = destinationDesc;
	}

	public Instant getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Instant applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CheckList getDocCheckList() {
		return docCheckList;
	}

	public void setDocCheckList(CheckList docCheckList) {
		this.docCheckList = docCheckList;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Instant getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Instant travelDate) {
		this.travelDate = travelDate;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}
