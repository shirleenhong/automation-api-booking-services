package com.cwt.bpg.cbt.exchange.order.calculator.tf;
import java.math.BigDecimal;

public class NettFareCalculator extends FeeCalculator {

	public BigDecimal getTotalFee(BigDecimal baseFare, BigDecimal totalAirlineCommission,
			BigDecimal totalReturnableOr) {
		
		return safeValue(baseFare).subtract(safeValue(totalAirlineCommission)).subtract(safeValue(totalReturnableOr));
	}
}
	