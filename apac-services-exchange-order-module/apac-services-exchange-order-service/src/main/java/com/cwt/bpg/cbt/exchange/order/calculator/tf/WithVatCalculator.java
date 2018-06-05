package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfPlusVatCalculator")
public class WithVatCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(InAirFeesInput input, InAirFeesBreakdown breakdown) {

		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()))
				.add(safeValue(breakdown.getTotalGst()))
				.subtract(safeValue(breakdown.getTotalIataCommission()))
				.subtract(breakdown.getTotalOverheadCommission());
	}
}
