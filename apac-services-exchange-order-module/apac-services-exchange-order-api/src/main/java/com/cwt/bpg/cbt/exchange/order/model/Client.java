package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("clients")
@Indexes(@Index(fields = @Field("clientId")))

public class Client implements Serializable {

	private static final long serialVersionUID = 4416580993384869665L;
	
	private int clientId;
	private int cmpid;
	private String name;
	private String profileName;
	private Integer pricingId;
	private boolean exemptTax;
	private List<ProductMerchantFee> products;
	private List<CreditCardVendor> vendors;
	private List<Bank> banks;
	private List<ClientPricing> clientPricings;
	private boolean standardMfProduct;
	private boolean applyMfCc;
	private boolean applyMfBank;
	private Double merchantFee;
	private boolean lccSameAsInt;
	private String lccDdlFeeApply;
	private String intDdlFeeApply;

	public void setProducts(List<ProductMerchantFee> list) {
		this.products = list;
	}

	public void setVendors(List<CreditCardVendor> list) {
		this.vendors = list;
	}

	public void setBanks(List<Bank> list) {
		this.banks = list;
	}

	public List<ProductMerchantFee> getProducts() {
		return products;
	}

	public List<CreditCardVendor> getVendors() {
		return vendors;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getCmpid() {
		return cmpid;
	}

	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Integer getPricingId() {
		return pricingId;
	}

	public void setPricingId(Integer pricingId) {
		this.pricingId = pricingId;
	}

	public boolean getExemptTax() {
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
}
