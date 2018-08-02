package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.cwt.bpg.cbt.exchange.order.model.validator.CcNotNullOnInsertOnFopType;
import com.cwt.bpg.cbt.exchange.order.model.validator.NotEmptyOnInsert;
import com.cwt.bpg.cbt.exchange.order.model.validator.ValidateOnInsert;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;


@NotEmptyOnInsert.List({@NotEmptyOnInsert(dependentFields = {"productCode","vendor"})})
@CcNotNullOnInsertOnFopType.List({@CcNotNullOnInsertOnFopType(fopTypes ={"CC","CX4"})})
@ValidateOnInsert.List({@ValidateOnInsert(dependentFields={"serviceInfo.formOfPayment.creditCard","vendor"})})
@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class ExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = 79442657760597469L;

	@ApiModelProperty(required = true)
	private String accountNumber;

	@ApiModelProperty(required = true)
	private String passengerName;
	
	private ServiceInfo serviceInfo;
	
	private Vendor vendor;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant createDateTime;

    @ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
	private Instant updateDateTime;

	@ApiModelProperty(required = true)
	private String agentId;

	@ApiModelProperty(required = true)
	private String pcc;

	private String faxNumber;

	@ApiModelProperty(required = true)
	private String agentName;

	@ApiModelProperty(required = true)
	private String recordLocator;

	private String raiseCheque;

	private List<String> eoRemarks;

	private List<String> itineraryRemarks;
	
	private MiscInfo miscInfo;
	
	private Insurance insurance;
	
	private MiscChargeOrder miscChargeOrder;

	private String lastUpdatedByUser;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Instant getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Instant createDateTime) {
		this.createDateTime = createDateTime;
	}

    public Instant getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Instant updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPcc() {
		return pcc;
	}

	public void setPcc(String pcc) {
		this.pcc = pcc;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getRaiseCheque() {
		return raiseCheque;
	}

	public void setRaiseCheque(String raiseCheque) {
		this.raiseCheque = raiseCheque;
	}

	public List<String> getEoRemarks() {
		return eoRemarks;
	}

	public void setEoRemarks(List<String> eoRemarks) {
		this.eoRemarks = eoRemarks;
	}

	public List<String> getItineraryRemarks() {
		return itineraryRemarks;
	}

	public void setItineraryRemarks(List<String> itineraryRemarks) {
		this.itineraryRemarks = itineraryRemarks;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

    public String getLastUpdatedByUser() {
        return lastUpdatedByUser;
    }

    public void setLastUpdatedByUser(String lastUpdatedByUser) {
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

	public MiscInfo getMiscInfo() {
		return miscInfo;
	}

	public void setMiscInfo(MiscInfo miscInfo) {
		this.miscInfo = miscInfo;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public MiscChargeOrder getMiscChargeOrder() {
		return miscChargeOrder;
	}

	public void setMiscChargeOrder(MiscChargeOrder miscChargeOrder) {
		this.miscChargeOrder = miscChargeOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
    
}
