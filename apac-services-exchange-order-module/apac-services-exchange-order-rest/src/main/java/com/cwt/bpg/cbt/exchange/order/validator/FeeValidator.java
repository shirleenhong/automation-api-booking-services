package com.cwt.bpg.cbt.exchange.order.validator;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component
public class FeeValidator implements Validator<IndiaAirFeesInput> {

	@Override
	public void validate(IndiaAirFeesInput input) {
		if (input.isFeeOverride() && input.getFee() == null) {
			throw new IllegalArgumentException("Fee required when feeOverride is true.");
		}
	}
}
