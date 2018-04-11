package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.ArrayList;
import java.util.List;

public class Product {

	public static final String COLLECTION = "_productCodes";
	
	private List<Vendor> vendors = new ArrayList<Vendor>();
	
	private String countryCode;
	
	private String productCode;

	private String description;

	private Boolean enableCCFOP;

	private Boolean fullComm;

	private Integer gst;

	private Boolean mi;

	private String sortKey;

	private Boolean tktNo;

	private String tktPrefix;

	private String type;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnableCCFOP() {
		return this.enableCCFOP;
	}

	public void setEnableCCFOP(Boolean enableCCFOP) {
		this.enableCCFOP = enableCCFOP;
	}

	public Boolean getFullComm() {
		return this.fullComm;
	}

	public void setFullComm(Boolean fullComm) {
		this.fullComm = fullComm;
	}

	public Integer getGst() {
		return this.gst;
	}

	public void setGst(Integer gst) {
		this.gst = gst;
	}

	public Boolean getMi() {
		return this.mi;
	}

	public void setMi(Boolean mi) {
		this.mi = mi;
	}

	public String getSortKey() {
		return this.sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public Boolean getTktNo() {
		return this.tktNo;
	}

	public void setTktNo(Boolean tktNo) {
		this.tktNo = tktNo;
	}

	public String getTktPrefix() {
		return this.tktPrefix;
	}

	public void setTktPrefix(String tktPrefix) {
		this.tktPrefix = tktPrefix;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}
	
	

}