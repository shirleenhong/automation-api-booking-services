package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.mongodb.morphia.annotations.*;

@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class IndiaExchangeOrder extends BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = -5915661302682405152L;
	
	private IndiaServiceInfo serviceInfo;
	
	private BaseVendor vendor;

	private IndiaMiscInfo miscInfo;

	private AssocServiceInfo assocServiceInfo;

	private Request request;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant createDateTime;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant updateDateTime;

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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public IndiaServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(IndiaServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	

}
