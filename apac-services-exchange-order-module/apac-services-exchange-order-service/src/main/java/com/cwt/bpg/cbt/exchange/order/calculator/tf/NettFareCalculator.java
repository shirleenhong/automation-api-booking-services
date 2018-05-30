package com.cwt.bpg.cbt.exchange.order.calculator.tf;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

@Component("tfNettFareCalculator")
public class NettFareCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).subtract(safeValue(breakdown.getTotalIataCommission()))
				.subtract(safeValue(breakdown.getTotalOverheadCommission()));
	}
}
