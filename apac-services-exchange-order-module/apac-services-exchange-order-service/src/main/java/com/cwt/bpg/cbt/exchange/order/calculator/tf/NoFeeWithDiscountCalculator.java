package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripType;

@Component("noFeeWithDiscountCalculator")
public class NoFeeWithDiscountCalculator extends FeeCalculator {

	@Override
	public BigDecimal getMfOnTf(IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
		
		if (TripType.isInternational(input.getTripType())) {
			return null;
		}
		else {
			return super.getMfOnTf(input, breakdown, totalGstOnTf);
		}
	}
}
