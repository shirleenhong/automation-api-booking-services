package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

import java.math.BigDecimal;

public class BaseAndYqCalculator extends FeeCalculator {

	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).add(safeValue(input.getYqTax()));
	}
}
