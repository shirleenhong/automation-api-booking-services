package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.FeesInput;

public class TransactionFeeCalculator extends CommonCalculator {

	public FeesBreakdown calculate(FeesInput genericInput) {
		return new FeesBreakdown();
	}
}
