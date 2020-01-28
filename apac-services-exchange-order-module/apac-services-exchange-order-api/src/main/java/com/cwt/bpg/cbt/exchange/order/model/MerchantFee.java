package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "clientMerchantFee", noClassnameStored = true)
@Indexes(@Index(fields = { @Field("countryCode"), @Field("clientAccountNumber") }))
public class MerchantFee implements Serializable {

	private static final long serialVersionUID = -1922100420586710851L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	@ApiModelProperty(hidden = true)
	private ObjectId id;
	
	private boolean includeTransactionFee;

	private Double merchantFeePercent;

	private String clientType;

	private String clientName;

	private String countryCode;

	private String clientAccountNumber;

	private List<MerchantFeeCreditCardVendor> mfCcs;

	public MerchantFee() {
		merchantFeePercent = 0d;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public boolean isIncludeTransactionFee() {
		return includeTransactionFee;
	}

	public void setIncludeTransactionFee(boolean includeTransactionFee) {
		this.includeTransactionFee = includeTransactionFee;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}

	public void setMfCcs(List<MerchantFeeCreditCardVendor> mfCcs)
	{
		this.mfCcs = mfCcs;
	}

	public List<MerchantFeeCreditCardVendor> getMfCcs()
	{
		return mfCcs;
	}

}
