package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "countryCode", visible = true)
@JsonSubTypes({
		@JsonSubTypes.Type(value = HkSgAirFeesBreakdown.class, name = "HK"),
		@JsonSubTypes.Type(value = HkSgAirFeesBreakdown.class, name = "SG"),
		@JsonSubTypes.Type(value = InAirFeesBreakdown.class, name = "IN")})
public abstract class AirFeesBreakdown extends FeesBreakdown {
	private static final long serialVersionUID = 8544229065892237666L;
}
