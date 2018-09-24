package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

public class ServiceInfo implements Serializable {

	private static final long serialVersionUID = 3555913363549766564L;

	@ApiModelProperty(allowableValues = "DU, DB, MN, TF, MG, TP")
	private ClientType clientType;

	private String fareType;

	private String vendorContactPerson;

	private String insurancePlan;

	private BigDecimal sellingFareToClient;

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
	private Boolean merchantFeeVendorHandlingFlag;

	private BigDecimal merchantFeeVendorHandling;

	private BigDecimal cwtHandling;

	@ApiModelProperty(value = "Merchant Fee on CWT Handling")
	private Boolean merchantFeeCwtHandlingFlag;

	private BigDecimal merchantFeeCwtHandling;

	private BigDecimal publishedFare;

	private BigDecimal sellingPrice;

	private BigDecimal commission;

	private BigDecimal discount;

	private BigDecimal gst;

	@ApiModelProperty(value = "CWT Absorb GST")
	private Boolean gstAbsorb;

	private BigDecimal merchantFee;

	@ApiModelProperty(value = "CWT Absorb Merchant Fee")
	private Boolean merchantFeeAbsorb;

	private Boolean uatp;

	private BigDecimal totalSellingPrice;

	private BigDecimal serviceFee;

	@ApiModelProperty(value = "TF in NRCC")
	private Boolean tfInNrcc;

	private BigDecimal fuelSurcharge;

	private String countryCode;

	private String type;

	private String entries;

	private Integer validityLength;

	private String validityType;

	private String processingType;

	private Integer processingLength;

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

	private Route returnRoute;

	private String cabin;

	private Flight departure;

	private Flight arrival;

	private AdditionalInfo additionalInfo;

	private Ticket ticket;

    private String prefixConsolidatorEoNumber;

    private String consolidatorEoNumber;

    private String fileFareNumber;

	@Valid
	private FormOfPayment formOfPayment;

    public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
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

	public BigDecimal getCwtHandling() {
		return cwtHandling;
	}

	public void setCwtHandling(BigDecimal cwtHandling) {
		this.cwtHandling = cwtHandling;
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

	public Boolean getGstAbsorb() {
		return gstAbsorb;
	}

	public void setGstAbsorb(Boolean gstAbsorb) {
		this.gstAbsorb = gstAbsorb;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public Boolean getMerchantFeeAbsorb() {
		return merchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(Boolean merchantFeeAbsorb) {
		this.merchantFeeAbsorb = merchantFeeAbsorb;
	}

	public Boolean getUatp() {
		return uatp;
	}

	public void setUatp(Boolean uatp) {
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

	public Boolean getTfInNrcc() {
		return tfInNrcc;
	}

	public void setTfInNrcc(Boolean tfInNrcc) {
		this.tfInNrcc = tfInNrcc;
	}

	public BigDecimal getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(BigDecimal fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
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

	public Integer getValidityLength() {
		return validityLength;
	}

	public void setValidityLength(Integer validityLength) {
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

	public Integer getProcessingLength() {
		return processingLength;
	}

	public void setProcessingLength(Integer processingLength) {
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

	public Route getReturnRoute() {
		return returnRoute;
	}

	public void setReturnRoute(Route returnRoute) {
		this.returnRoute = returnRoute;
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

	public Boolean getMerchantFeeVendorHandlingFlag() {
		return merchantFeeVendorHandlingFlag;
	}

	public void setMerchantFeeVendorHandlingFlag(Boolean merchantFeeVendorHandlingFlag) {
		this.merchantFeeVendorHandlingFlag = merchantFeeVendorHandlingFlag;
	}

	public BigDecimal getMerchantFeeVendorHandling() {
		return merchantFeeVendorHandling;
	}

	public void setMerchantFeeVendorHandling(BigDecimal merchantFeeVendorHandling) {
		this.merchantFeeVendorHandling = merchantFeeVendorHandling;
	}

	public Boolean getMerchantFeeCwtHandlingFlag() {
		return merchantFeeCwtHandlingFlag;
	}

	public void setMerchantFeeCwtHandlingFlag(Boolean merchantFeeCwtHandlingFlag) {
		this.merchantFeeCwtHandlingFlag = merchantFeeCwtHandlingFlag;
	}

	public BigDecimal getMerchantFeeCwtHandling() {
		return merchantFeeCwtHandling;
	}

	public void setMerchantFeeCwtHandling(BigDecimal merchantFeeCwtHandling) {
		this.merchantFeeCwtHandling = merchantFeeCwtHandling;
	}

    public BigDecimal getSellingFareToClient()
    {
        return sellingFareToClient;
    }

    public void setSellingFareToClient(BigDecimal sellingFareToClient)
    {
        this.sellingFareToClient = sellingFareToClient;
    }

    public BigDecimal getPublishedFare()
    {
        return publishedFare;
    }

    public void setPublishedFare(BigDecimal publishedFare)
    {
        this.publishedFare = publishedFare;
    }

    public String getPrefixConsolidatorEoNumber()
    {
        return prefixConsolidatorEoNumber;
    }

    public void setPrefixConsolidatorEoNumber(String prefixConsolidatorEoNumber)
    {
        this.prefixConsolidatorEoNumber = prefixConsolidatorEoNumber;
    }

    public String getConsolidatorEoNumber()
    {
        return consolidatorEoNumber;
    }

    public void setConsolidatorEoNumber(String consolidatorEoNumber)
    {
        this.consolidatorEoNumber = consolidatorEoNumber;
    }

    public String getFileFareNumber()
    {
        return fileFareNumber;
    }

    public void setFileFareNumber(String fileFareNumber)
    {
        this.fileFareNumber = fileFareNumber;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }
}
