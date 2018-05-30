package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

import java.math.BigDecimal;

public class WithVatCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {

		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()))
				.add(safeValue(breakdown.getTotalGst()))
				.subtract(safeValue(breakdown.getTotalIataCommission()))
				.subtract(breakdown.getTotalOverheadCommission());
	}
}
