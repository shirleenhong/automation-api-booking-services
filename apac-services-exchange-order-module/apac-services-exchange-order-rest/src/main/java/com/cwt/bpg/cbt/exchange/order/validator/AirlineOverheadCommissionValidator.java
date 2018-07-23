package com.cwt.bpg.cbt.exchange.order.validator;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component
public class AirlineOverheadCommissionValidator implements Validator<IndiaAirFeesInput> {

	@Override
	public void validate(IndiaAirFeesInput input) {
		if (input.isAirlineOverheadCommissionByPercent()
				&& input.getAirlineOverheadCommissionPercent() == null) {

			throw new IllegalArgumentException(
					"AirlineOverheadCommissionPercent required when AirlineOverheadCommissionByPercent is true.");
		}
		else if (!input.isAirlineOverheadCommissionByPercent()
				&& input.getAirlineOverheadCommission() == null) {

			throw new IllegalArgumentException(
					"AirlineOverheadCommission required when AirlineOverheadCommissionByPercent is false.");
		}
	}
}
