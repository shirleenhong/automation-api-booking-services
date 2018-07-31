package com.cwt.bpg.cbt.exchange.order.model.india;

import java.math.BigDecimal;
import java.time.Instant;

import com.cwt.bpg.cbt.exchange.order.model.EoAction;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public class IndiaExchangeOrder {

	private String eoNumber;

	private Request request;

	private EoAction eoAction;

	private EoStatus status;

	private Instant createDateTime;

	private Instant updateDateTime;

	private String agentId;

	private String agentName;

	private String countryCode;

	private String pcc;

	private String productCode;

	private Vendor vendor;

	private BigDecimal total;

	public String getEoNumber() {
		return eoNumber;
	}

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
