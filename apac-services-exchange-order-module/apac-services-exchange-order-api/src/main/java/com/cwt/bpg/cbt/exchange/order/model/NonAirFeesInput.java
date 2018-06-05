package com.cwt.bpg.cbt.exchange.order.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "countryCode", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InNonAirFeesInput.class, name = "IN"),
        @JsonSubTypes.Type(value = HkSgNonAirFeesInput.class, name = "SG"),
        @JsonSubTypes.Type(value = HkSgNonAirFeesInput.class, name = "HK")})
public abstract class NonAirFeesInput extends FeesInput{
}