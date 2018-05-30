package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

@Component("tfPlusVatCalculator")
public class WithVatCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {

		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()))
				.add(safeValue(breakdown.getTotalGst()))
				.subtract(safeValue(breakdown.getTotalIataCommission()))
				.subtract(breakdown.getTotalOverheadCommission());
	}
}
