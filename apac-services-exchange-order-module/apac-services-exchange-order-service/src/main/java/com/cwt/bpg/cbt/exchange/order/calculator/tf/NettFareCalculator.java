package com.cwt.bpg.cbt.exchange.order.calculator.tf;
import java.math.BigDecimal;

import org.springframework.util.ObjectUtils;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class NettFareCalculator extends FeeCalculator {

	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {

		if (!ObjectUtils.isEmpty(input) && !ObjectUtils.isEmpty(breakdown)) {
			return safeValue(input.getBaseFare()).subtract(safeValue(breakdown.getTotalIataCommission()))
					.subtract(safeValue(breakdown.getTotalReturnableOr()));
		}
		return null;
	}
}
