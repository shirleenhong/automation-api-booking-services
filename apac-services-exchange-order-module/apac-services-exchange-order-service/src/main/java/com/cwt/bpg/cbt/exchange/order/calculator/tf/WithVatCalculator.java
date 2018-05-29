package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

public class WithVatCalculator extends TransactionFeeCalculator {
	public BigDecimal getTotalFee(BigDecimal baseFare, BigDecimal totalTaxes, BigDecimal totalGst,
			BigDecimal totalAirlineCommission, BigDecimal totalReturnableOr) {

		return safeValue(baseFare).add(safeValue(totalTaxes)).add(safeValue(totalGst))
				.subtract(safeValue(totalAirlineCommission)).subtract(totalReturnableOr);
	}
}
