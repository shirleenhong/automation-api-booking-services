package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.exchange.order.model.validator.CcNotNullOnInsertOnFopType;
import com.cwt.bpg.cbt.exchange.order.model.validator.NotEmptyOnInsert;
import com.cwt.bpg.cbt.exchange.order.model.validator.ValidateOnInsert;

import io.swagger.annotations.ApiModelProperty;


@NotEmptyOnInsert(dependentFields = {"productCode","vendor"})
@CcNotNullOnInsertOnFopType(fopTypes ={"CC","CX4"})
@ValidateOnInsert(dependentFields={"serviceInfo.formOfPayment.creditCard","vendor"})
@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class ExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = 79442657760597469L;

	@ApiModelProperty(required = true)
	private String accountNumber;

	@ApiModelProperty(required = true)
	private String passengerName;

	private ServiceInfo serviceInfo;

	@ApiModelProperty(required = true)
	private Vendor vendor;

	@ApiModelProperty(required = true)
	private String agentId;

	@ApiModelProperty(required = true)
	private String pcc;

	private String faxNumber;

	@ApiModelProperty(required = true)
	private String agentName;

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

//	@Override
	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	@Override
    public Vendor getVendor() {
        return vendor;
    }

//    @Override
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
