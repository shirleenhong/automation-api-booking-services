package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfFullFareCalculator")
public class FullFareCalculator extends FeeCalculator {
	
	@Override
	public BigDecimal getTotalFee(InAirFeesInput input, TransactionFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()));
	}
}
