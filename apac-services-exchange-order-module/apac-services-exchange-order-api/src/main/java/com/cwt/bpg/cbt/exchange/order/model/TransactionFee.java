package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TransactionFee implements Serializable {

	private static final long serialVersionUID = 3449210642017436982L;

	private Integer feeId;
	private List<String> territoryCodes;
	private String type;
	private BigDecimal amount;
	private String operator;
	private BigDecimal extraAmount;
	private BigDecimal perAmount;
	private BigDecimal minAmount;
	private BigDecimal maxAmount;
	private BigDecimal startAmount;
	private BigDecimal endAmount;
	private BigDecimal startCoupon;
	private BigDecimal endCoupon;
	private BigDecimal threshold;

	public Integer getFeeId() {
		return feeId;
	}

	public void setFeeId(Integer feeId) {
		this.feeId = feeId;
	}

	public List<String> getTerritoryCodes() {
		return territoryCodes;
	}

	public void setTerritoryCodes(List<String> territoryCodes) {
		this.territoryCodes = territoryCodes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getExtraAmount() {
		return extraAmount;
	}

	public void setExtraAmount(BigDecimal extraAmount) {
		this.extraAmount = extraAmount;
	}

	public BigDecimal getPerAmount() {
		return perAmount;
	}

	public void setPerAmount(BigDecimal perAmount) {
		this.perAmount = perAmount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}

	public BigDecimal getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public BigDecimal getStartCoupon() {
		return startCoupon;
	}

	public void setStartCoupon(BigDecimal startCoupon) {
		this.startCoupon = startCoupon;
	}

	public BigDecimal getEndCoupon() {
		return endCoupon;
	}

	public void setEndCoupon(BigDecimal endCoupon) {
		this.endCoupon = endCoupon;
	}

	public BigDecimal getThreshold() {
		return threshold;
	}

	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}
}
