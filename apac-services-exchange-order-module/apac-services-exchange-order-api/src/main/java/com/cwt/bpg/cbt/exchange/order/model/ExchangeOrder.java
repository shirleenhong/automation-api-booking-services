package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class ExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = 79442657760597469L;

	@ApiModelProperty(required = true)
	private String accountNumber;

	@ApiModelProperty(required = true)
	private String passengerName;

	@Valid
	private ServiceInfo serviceInfo;

	@NotNull
	@Valid
	@ApiModelProperty(required = true)
	private Vendor vendor;

	@ApiModelProperty(required = true)
	private String agentId;

	@ApiModelProperty(required = true)
	private String pcc;

	@ApiModelProperty(required = true)
	private String agentName;

	private String raiseCheque;

	private MiscInfo miscInfo;
	
	private Insurance insurance;
	
	private MiscChargeOrder miscChargeOrder;

	private String lastUpdatedByUser;
	
	private List<String> pdfDescriptions;

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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRaiseCheque() {
		return raiseCheque;
	}

	public void setRaiseCheque(String raiseCheque) {
		this.raiseCheque = raiseCheque;
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

	@Override
	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	@Override
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

	public List<String> getPdfDescriptions() {
		return pdfDescriptions;
	}

	public void setPdfDescriptions(List<String> pdfDescriptions) {
		this.pdfDescriptions = pdfDescriptions;
	}
}
