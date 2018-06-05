package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "countryCode", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HkSgAirFeesInput.class, name = "HK"),
        @JsonSubTypes.Type(value = HkSgAirFeesInput.class, name = "SG"),
        @JsonSubTypes.Type(value = InAirFeesInput.class, name = "IN")})
public abstract class AirFeesInput extends FeesInput {
	private static final long serialVersionUID = 4361130224212373736L;
}
