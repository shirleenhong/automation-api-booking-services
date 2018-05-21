package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

public class TransactionFee {
	
	private String territoryCode;
	private String type;
	private Double tfAmount;
	private String operator;
	private Double extraAmount;
	private Double perAmount;
	private Double minAmount;
	private Double maxAmount;
	private Double startAmount;
	private Double endAmount;
	private Double startCoupon;
	private Double endCoupon;
	private Double threshold;
	
	public String getTerritoryCode() {
		return territoryCode;
	}
	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getTfAmount() {
		return tfAmount;
	}
	public void setTfAmount(Double tfAmount) {
		this.tfAmount = tfAmount;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Double getExtraAmount() {
		return extraAmount;
	}
	public void setExtraAmount(Double extraAmount) {
		this.extraAmount = extraAmount;
	}
	public Double getPerAmount() {
		return perAmount;
	}
	public void setPerAmount(Double perAmount) {
		this.perAmount = perAmount;
	}
	public Double getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Double getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(Double startAmount) {
		this.startAmount = startAmount;
	}
	public Double getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}
	public Double getStartCoupon() {
		return startCoupon;
	}
	public void setStartCoupon(Double startCoupon) {
		this.startCoupon = startCoupon;
	}
	public Double getEndCoupon() {
		return endCoupon;
	}
	public void setEndCoupon(Double endCoupon) {
		this.endCoupon = endCoupon;
	}
	public Double getThreshold() {
		return threshold;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
}
