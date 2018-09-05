package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public class Forex implements Serializable {

	private static final long serialVersionUID = 2584567094454585055L;

	private String forexType;

	private String travelType;

    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant travelDate;

	private String expectedDeliveryDate;

	private String deliveryAddress;

	private String passengerEmployeeId;

	private String businessUnit;

	private String passportNumber;

	private String contactNumber;

	private RequestAmount requestAmount;

	private CheckList docCheckList;

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

	public CheckList getDocCheckList() {
		return docCheckList;
	}

	public void setDocCheckList(CheckList docCheckList) {
		this.docCheckList = docCheckList;
	}

    public Instant getTravelDate()
    {
        return travelDate;
    }

    public void setTravelDate(Instant travelDate)
    {
        this.travelDate = travelDate;
    }
}
