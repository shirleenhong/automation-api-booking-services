package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

import java.math.BigDecimal;

public class BasicCalculator extends FeeCalculator {
	@Override
	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {
		return input.getBaseFare();
	}
}
