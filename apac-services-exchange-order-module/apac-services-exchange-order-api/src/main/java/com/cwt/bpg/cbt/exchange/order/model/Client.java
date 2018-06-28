package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity("clients")
@Indexes(@Index(fields = @Field("clientId")))
@JsonIgnoreProperties(value = { "cmpid" })
public class Client implements Serializable {

	private static final long serialVersionUID = 4416580993384869665L;
	
	private int clientId;
	private String name;
	private String clientAccountNumber;
	private Integer pricingId;
	private boolean exemptTax;
	private List<ProductMerchantFee> mfProducts;
	private List<CreditCardVendor> mfCcs;
	private List<Bank> mfBanks;
	private List<ClientPricing> clientPricings;
	private boolean standardMfProduct;
	private boolean applyMfCc;
	private boolean applyMfBank;
	private Double merchantFee;
	private boolean lccSameAsInt;
	private String lccDdlFeeApply;
	private String intDdlFeeApply;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

	public Integer getPricingId() {
		return pricingId;
	}

	public void setPricingId(Integer pricingId) {
		this.pricingId = pricingId;
	}

	public boolean isExemptTax() {
		return exemptTax;
	}

	public void setExemptTax(boolean exemptTax) {
		this.exemptTax = exemptTax;
	}

	public List<ClientPricing> getClientPricings() {
		return clientPricings;
	}

	public void setClientPricings(List<ClientPricing> clientPricings) {
		this.clientPricings = clientPricings;
	}

	public boolean isStandardMfProduct() {
		return standardMfProduct;
	}

	public void setStandardMfProduct(boolean standardMfProduct) {
		this.standardMfProduct = standardMfProduct;
	}

	public boolean isApplyMfCc() {
		return applyMfCc;
	}

	public void setApplyMfCc(boolean applyMfCc) {
		this.applyMfCc = applyMfCc;
	}

	public boolean isApplyMfBank() {
		return applyMfBank;
	}

	public void setApplyMfBank(boolean applyMfBank) {
		this.applyMfBank = applyMfBank;
	}

	public Double getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(Double merchantFee) {
		this.merchantFee = merchantFee;
	}

	public boolean getLccSameAsInt() {
		return lccSameAsInt;
	}

	public void setLccSameAsInt(boolean lccSameAsInt) {
		this.lccSameAsInt = lccSameAsInt;
	}

	public String getLccDdlFeeApply() {
		return lccDdlFeeApply;
	}

	public void setLccDdlFeeApply(String lccDdlFeeApply) {
		this.lccDdlFeeApply = lccDdlFeeApply;
	}

	public String getIntDdlFeeApply() {
		return intDdlFeeApply;
	}

	public void setIntDdlFeeApply(String intDdlFeeApply) {
		this.intDdlFeeApply = intDdlFeeApply;
	}

	public List<ProductMerchantFee> getMfProducts() {
		return mfProducts;
	}

	public void setMfProducts(List<ProductMerchantFee> mfProducts) {
		this.mfProducts = mfProducts;
	}

	public List<CreditCardVendor> getMfCcs() {
		return mfCcs;
	}

	public void setMfCcs(List<CreditCardVendor> mfCcs) {
		this.mfCcs = mfCcs;
	}

	public List<Bank> getMfBanks() {
		return mfBanks;
	}

	public void setMfBanks(List<Bank> mfBanks) {
		this.mfBanks = mfBanks;
	}	
}
