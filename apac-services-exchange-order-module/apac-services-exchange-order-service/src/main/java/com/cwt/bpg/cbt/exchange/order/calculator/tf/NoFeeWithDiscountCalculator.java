package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

import java.math.BigDecimal;

public class NoFeeWithDiscountCalculator extends FeeCalculator {

	public BigDecimal getMfOnTf(int tripType, TransactionFeesInput input, BigDecimal totalGstOnTf) {
		if (tripType == TripTypes.INT.getId()) {
			return null;
		}else {
			return super.getMfOnTf(tripType, input, totalGstOnTf);
		}
	}
}
