package com.cwt.bpg.cbt.exchange.order.validator;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component
public class OthTaxValidator implements Validator<IndiaAirFeesInput> {

	@Override
	public void validate(IndiaAirFeesInput input) {
		if (input.getOthTax1() == null && input.getOthTax2() == null && input.getOthTax3() == null) {
			throw new IllegalArgumentException("At least one tax required.");
		}
	}
}
