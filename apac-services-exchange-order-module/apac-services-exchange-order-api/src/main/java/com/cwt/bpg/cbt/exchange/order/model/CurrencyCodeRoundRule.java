package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("currency")
@Indexes(@Index(fields = @Field("currencyCode")))
public class CurrencyCodeRoundRule implements Serializable {
	
	private static final long serialVersionUID = 3562863330210125375L;

	private String currencyCode;
    
	private String description;
    
	private Integer decimal;
    
	private String roundRule;
    
	private Double roundUnit;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDecimal() {
		return decimal;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public String getRoundRule() {
		return roundRule;
	}

	public void setRoundRule(String roundRule) {
		this.roundRule = roundRule;
	}

	public Double getRoundUnit() {
		return roundUnit;
	}

	public void setRoundUnit(Double roundUnit) {
		this.roundUnit = roundUnit;
	}
}
