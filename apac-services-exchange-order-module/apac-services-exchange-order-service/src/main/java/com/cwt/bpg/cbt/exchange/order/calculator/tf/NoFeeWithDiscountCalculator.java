package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

@Component("noFeeWithDiscountCalculator")
public class NoFeeWithDiscountCalculator extends FeeCalculator {

	@Override
	public BigDecimal getMfOnTf(TransactionFeesInput input, 
			TransactionFeesBreakdown breakdown, 
			BigDecimal totalGstOnTf) {
		
		if (TripTypes.isInternational(input.getTripType())) {
			return null;
		}
		else {
			return super.getMfOnTf(input, breakdown, totalGstOnTf);
		}
	}
}
