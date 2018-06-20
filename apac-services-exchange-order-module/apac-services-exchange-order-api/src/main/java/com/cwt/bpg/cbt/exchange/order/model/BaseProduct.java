package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Vendor> vendors = new ArrayList<>();

	private String productCode;

	private String description;

	private Double gstPercent;

	private String sortKey;

	private String ticketPrefix;

	private String type;

	public BaseProduct() {
		// Empty constructor
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getGstPercent() {
		return gstPercent;
	}

	public void setGstPercent(Double gstPercent) {
		this.gstPercent = gstPercent;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTicketPrefix() {
		return ticketPrefix;
	}

	public void setTicketPrefix(String ticketPrefix) {
		this.ticketPrefix = ticketPrefix;
	}

}
