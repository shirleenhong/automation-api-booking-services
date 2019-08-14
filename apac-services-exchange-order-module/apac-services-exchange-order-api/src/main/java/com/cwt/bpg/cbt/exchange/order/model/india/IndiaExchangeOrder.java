package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;

@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class IndiaExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = -5915661302682405152L;
	
	@Valid
	private NonAirCharges nonAirCharges;

	private AirCharges airCharges;

	@Valid
	@NotNull
	private IndiaVendor vendor;

	private IndiaMiscInfo miscInfo;

	@Valid
	private List<AssociatedCharges> associatedChargesList;

	@Valid
	private Request request;

	private String agentId;

	private String agentName;

	private String pcc;

	private String passengerName;
	
	public IndiaMiscInfo getMiscInfo() {
		return miscInfo;
	}

	public void setMiscInfo(IndiaMiscInfo miscInfo) {
		this.miscInfo = miscInfo;
	}

	public List<AssociatedCharges> getAssociatedChargesList() {
		return associatedChargesList;
	}

	public void setAssociatedChargesList(List<AssociatedCharges> associatedChargesList) {
		this.associatedChargesList = associatedChargesList;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPcc() {
		return pcc;
	}

	public void setPcc(String pcc) {
		this.pcc = pcc;
	}

	public NonAirCharges getNonAirCharges() {
		return nonAirCharges;
	}

    public void setNonAirCharges(NonAirCharges nonAirCharges) {
        this.nonAirCharges = nonAirCharges;
    }

    public AirCharges getAirCharges() {
        return airCharges;
    }

    public void setAirCharges(AirCharges airCharges) {
        this.airCharges = airCharges;
    }

	@Override
	public IndiaVendor getVendor() {
		return vendor;
	}

	public void setVendor(IndiaVendor vendor) {
		this.vendor = vendor;
	}

	public String getPassengerName() {
		if (StringUtils.isNotBlank(passengerName)) {
			return passengerName;
		}
		else if (request != null 
				&& request.getAir() != null 
				&& StringUtils.isNotBlank(request.getAir().getPassengerName())) {
			return request.getAir().getPassengerName();
		}
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
}
