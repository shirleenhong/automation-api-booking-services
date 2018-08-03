package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import com.cwt.bpg.cbt.exchange.order.model.validator.CcNotNullOnInsertOnFopType;
import com.cwt.bpg.cbt.exchange.order.model.validator.NotEmptyOnInsert;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;

@NotEmptyOnInsert(dependentFields = {"productCode","vendor"})
@CcNotNullOnInsertOnFopType(fopTypes ={"CC","CX4"})
@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class IndiaExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = -5915661302682405152L;

	private IndiaServiceInfo serviceInfo;
	
	private IndiaVendor vendor;

	private IndiaMiscInfo miscInfo;

	private AssocServiceInfo assocServiceInfo;

	private Request request;

	private String agentId;

	private String agentName;

	private String pcc;

	public IndiaMiscInfo getMiscInfo() {
		return miscInfo;
	}

	public void setMiscInfo(IndiaMiscInfo miscInfo) {
		this.miscInfo = miscInfo;
	}

	public AssocServiceInfo getAssocServiceInfo() {
		return assocServiceInfo;
	}

	public void setAssocServiceInfo(AssocServiceInfo assocServiceInfo) {
		this.assocServiceInfo = assocServiceInfo;
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

	@Override
	public IndiaServiceInfo getServiceInfo() {
		return serviceInfo;
	}

//    @Override
    public void setServiceInfo(IndiaServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

	@Override
	public IndiaVendor getVendor() {
		return vendor;
	}

//	@Override
	public void setVendor(IndiaVendor vendor) {
		this.vendor = vendor;
	}
	
	

}
