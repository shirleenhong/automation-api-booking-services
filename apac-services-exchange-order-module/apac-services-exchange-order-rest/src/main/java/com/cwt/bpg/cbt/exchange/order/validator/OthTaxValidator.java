package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import org.springframework.stereotype.Component;

@Component
public class OthTaxValidator {

	public void validate(IndiaAirFeesInput input) {

		if (input.getTax1() == null && input.getTax2() == null) {

			throw new IllegalArgumentException("At least one tax required.");
		}
	}
}
