package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class GrossFareCalculator extends FeeCalculator {

	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {

		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()))
				.add(safeValue(breakdown.getTotalGst()));

	}
}
