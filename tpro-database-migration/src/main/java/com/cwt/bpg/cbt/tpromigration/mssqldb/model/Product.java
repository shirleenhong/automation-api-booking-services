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

	private Integer Number;

	private String name;

	private String gDSCode;

	private Integer gST;

	private Integer oT1;

	private Integer oT2;

	private Boolean requireCDR;

	private Boolean requireTicket;

	private Boolean suppressMI;

	private Float FeeCode;

	private String gSTFormula;

	private Boolean appMF;

	private Boolean overridePC;

	private Boolean hotelFee;

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

	public Integer getNumber() {
		return Number;
	}

	public void setNumber(Integer number) {
		Number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getgDSCode() {
		return gDSCode;
	}

	public void setgDSCode(String gDSCode) {
		this.gDSCode = gDSCode;
	}

	public Integer getgST() {
		return gST;
	}

	public void setgST(Integer gST) {
		this.gST = gST;
	}

	public Integer getoT1() {
		return oT1;
	}

	public void setoT1(Integer oT1) {
		this.oT1 = oT1;
	}

	public Integer getoT2() {
		return oT2;
	}

	public void setoT2(Integer oT2) {
		this.oT2 = oT2;
	}

	public Boolean getRequireCDR() {
		return requireCDR;
	}

	public void setRequireCDR(Boolean requireCDR) {
		this.requireCDR = requireCDR;
	}

	public Boolean getRequireTicket() {
		return requireTicket;
	}

	public void setRequireTicket(Boolean requireTicket) {
		this.requireTicket = requireTicket;
	}

	public Boolean getSuppressMI() {
		return suppressMI;
	}

	public void setSuppressMI(Boolean suppressMI) {
		this.suppressMI = suppressMI;
	}

	public Float getFeeCode() {
		return FeeCode;
	}

	public void setFeeCode(Float feeCode) {
		FeeCode = feeCode;
	}

	public String getgSTFormula() {
		return gSTFormula;
	}

	public void setgSTFormula(String gSTFormula) {
		this.gSTFormula = gSTFormula;
	}

	public Boolean getAppMF() {
		return appMF;
	}

	public void setAppMF(Boolean appMF) {
		this.appMF = appMF;
	}

	public Boolean getOverridePC() {
		return overridePC;
	}

	public void setOverridePC(Boolean overridePC) {
		this.overridePC = overridePC;
	}

	public Boolean getHotelFee() {
		return hotelFee;
	}

	public void setHotelFee(Boolean hotelFee) {
		this.hotelFee = hotelFee;
	}
}