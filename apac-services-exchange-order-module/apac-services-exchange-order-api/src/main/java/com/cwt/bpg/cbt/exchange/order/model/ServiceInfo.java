package com.cwt.bpg.cbt.exchange.order.model;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class ServiceInfo implements Serializable {

	private static final long serialVersionUID = 3555913363549766564L;

	private ClientTypes clientType;

	private String fareType;

	private String vendorContactPerson;

	private String insurancePlan;

	private BigDecimal nettCost;

	private BigDecimal nettFare;

	private BigDecimal grossFare;

	private BigDecimal tax1;

	private String taxCode1;

	private BigDecimal tax2;

	private String taxCode2;

	private BigDecimal nettCostInEo;

	private BigDecimal vendorHandling;

	@ApiModelProperty(value = "Merchant Fee on Vendor Handling")
	private boolean isMerchantFeeVendorHandling;

	private BigDecimal merchantFeeVendorHandling;

	private BigDecimal cwtHandling;

	@ApiModelProperty(value = "Merchant Fee on CWT Handling")
	private boolean isMerchantFeeCwtHandling;

	private BigDecimal merchantFeeCwtHandling;

	private BigDecimal sellingPrice;

	private BigDecimal commission;

	private BigDecimal discount;

	private BigDecimal gst;

	@ApiModelProperty(value = "CWT Absorb GST")
	private boolean cwtGstAbsorb;

	private BigDecimal merchantFee;

	@ApiModelProperty(value = "CWT Absorb Merchant Fee")
	private boolean cwtAbsorbmerchantFee;

	private boolean uatp;

	private BigDecimal totalSellingPrice;

	private BigDecimal serviceFee;

	@ApiModelProperty(value = "TF in NRCC")
	private boolean tfInNrcc;

	private BigDecimal fuelSurcharge;

	private FormOfPayment formOfPayment;

	private String countryCode;

	private String type;

	private String entries;

	private int validityLength;

	private String validityType;

	private String processingType;

	private int processingLength;

	private String visaNumber;

	private String passengerId;

	private String eoNumber;

	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant departDateAndTime;

	private Route departRoute;

	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant returnDateAndTime;

	private Route returRoute;

	private String cabin;

	private Flight departure;

	private Flight arrival;

	private AdditionalInfo additionalInfo;

	private Ticket ticket;

	public ClientTypes getClientType() {
		return clientType;
	}

	public void setClientType(ClientTypes clientType) {
		this.clientType = clientType;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public String getVendorContactPerson() {
		return vendorContactPerson;
	}

	public void setVendorContactPerson(String vendorContactPerson) {
		this.vendorContactPerson = vendorContactPerson;
	}

	public String getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(String insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public BigDecimal getNettCost() {
		return nettCost;
	}

	public void setNettCost(BigDecimal nettCost) {
		this.nettCost = nettCost;
	}

	public BigDecimal getNettFare() {
		return nettFare;
	}

	public void setNettFare(BigDecimal nettFare) {
		this.nettFare = nettFare;
	}

	public BigDecimal getGrossFare() {
		return grossFare;
	}

	public void setGrossFare(BigDecimal grossFare) {
		this.grossFare = grossFare;
	}

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public String getTaxCode1() {
		return taxCode1;
	}

	public void setTaxCode1(String taxCode1) {
		this.taxCode1 = taxCode1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}

	public String getTaxCode2() {
		return taxCode2;
	}

	public void setTaxCode2(String taxCode2) {
		this.taxCode2 = taxCode2;
	}

	public BigDecimal getNettCostInEo() {
		return nettCostInEo;
	}

	public void setNettCostInEo(BigDecimal nettCostInEo) {
		this.nettCostInEo = nettCostInEo;
	}

	public BigDecimal getVendorHandling() {
		return vendorHandling;
	}

	public void setVendorHandling(BigDecimal vendorHandling) {
		this.vendorHandling = vendorHandling;
	}

	public boolean isMerchantFeeVendorHandling() {
		return isMerchantFeeVendorHandling;
	}

	public void setMerchantFeeVendorHandling(boolean merchantFeeVendorHandling) {
		isMerchantFeeVendorHandling = merchantFeeVendorHandling;
	}

	public BigDecimal getMerchantFeeVendorHandling() {
		return merchantFeeVendorHandling;
	}

	public void setMerchantFeeVendorHandling(BigDecimal merchantFeeVendorHandling) {
		this.merchantFeeVendorHandling = merchantFeeVendorHandling;
	}

	public BigDecimal getCwtHandling() {
		return cwtHandling;
	}

	public void setCwtHandling(BigDecimal cwtHandling) {
		this.cwtHandling = cwtHandling;
	}

	public boolean isMerchantFeeCwtHandling() {
		return isMerchantFeeCwtHandling;
	}

	public void setMerchantFeeCwtHandling(boolean merchantFeeCwtHandling) {
		isMerchantFeeCwtHandling = merchantFeeCwtHandling;
	}

	public BigDecimal getMerchantFeeCwtHandling() {
		return merchantFeeCwtHandling;
	}

	public void setMerchantFeeCwtHandling(BigDecimal merchantFeeCwtHandling) {
		this.merchantFeeCwtHandling = merchantFeeCwtHandling;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}

	public boolean isCwtGstAbsorb() {
		return cwtGstAbsorb;
	}

	public void setCwtGstAbsorb(boolean cwtGstAbsorb) {
		this.cwtGstAbsorb = cwtGstAbsorb;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public boolean isCwtAbsorbmerchantFee() {
		return cwtAbsorbmerchantFee;
	}

	public void setCwtAbsorbmerchantFee(boolean cwtAbsorbmerchantFee) {
		this.cwtAbsorbmerchantFee = cwtAbsorbmerchantFee;
	}

	public boolean isUatp() {
		return uatp;
	}

	public void setUatp(boolean uatp) {
		this.uatp = uatp;
	}

	public BigDecimal getTotalSellingPrice() {
		return totalSellingPrice;
	}

	public void setTotalSellingPrice(BigDecimal totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public boolean isTfInNrcc() {
		return tfInNrcc;
	}

	public void setTfInNrcc(boolean tfInNrcc) {
		this.tfInNrcc = tfInNrcc;
	}

	public BigDecimal getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(BigDecimal fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public FormOfPayment getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(FormOfPayment formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(String entries) {
		this.entries = entries;
	}

	public int getValidityLength() {
		return validityLength;
	}

	public void setValidityLength(int validityLength) {
		this.validityLength = validityLength;
	}

	public String getValidityType() {
		return validityType;
	}

	public void setValidityType(String validityType) {
		this.validityType = validityType;
	}

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}

	public int getProcessingLength() {
		return processingLength;
	}

	public void setProcessingLength(int processingLength) {
		this.processingLength = processingLength;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getEoNumber() {
		return eoNumber;
	}

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
	}

	public Instant getDepartDateAndTime() {
		return departDateAndTime;
	}

	public void setDepartDateAndTime(Instant departDateAndTime) {
		this.departDateAndTime = departDateAndTime;
	}

	public Route getDepartRoute() {
		return departRoute;
	}

	public void setDepartRoute(Route departRoute) {
		this.departRoute = departRoute;
	}

	public Instant getReturnDateAndTime() {
		return returnDateAndTime;
	}

	public void setReturnDateAndTime(Instant returnDateAndTime) {
		this.returnDateAndTime = returnDateAndTime;
	}

	public Route getReturRoute() {
		return returRoute;
	}

	public void setReturRoute(Route returRoute) {
		this.returRoute = returRoute;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public Flight getDeparture() {
		return departure;
	}

	public void setDeparture(Flight departure) {
		this.departure = departure;
	}

	public Flight getArrival() {
		return arrival;
	}

	public void setArrival(Flight arrival) {
		this.arrival = arrival;
	}

	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
