package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.EoAction;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.mongodb.morphia.annotations.*;

@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class IndiaExchangeOrder implements Serializable {

	private static final long serialVersionUID = -5915661302682405152L;

	@Id
	private String eoNumber;

	private IndiaServiceInfo serviceInfo;

	private IndiaVendor vendor;

	private IndiaMiscInfo miscInfo;

	private AssocServiceInfo assocServiceInfo;

	private Request request;

	private EoAction eoAction;

	private EoStatus status;

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

	private String countryCode;

	private String pcc;

	private String productCode;

	private BigDecimal total;

	public String getEoNumber() {
		return eoNumber;
	}

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
	}

	public IndiaServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(IndiaServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public IndiaVendor getVendor() {
		return vendor;
	}

	public void setVendor(IndiaVendor vendor) {
		this.vendor = vendor;
	}

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

	public EoAction getEoAction() {
		return eoAction;
	}

	public void setEoAction(EoAction eoAction) {
		this.eoAction = eoAction;
	}

	public EoStatus getStatus() {
		return status;
	}

	public void setStatus(EoStatus status) {
		this.status = status;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPcc() {
		return pcc;
	}

	public void setPcc(String pcc) {
		this.pcc = pcc;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
