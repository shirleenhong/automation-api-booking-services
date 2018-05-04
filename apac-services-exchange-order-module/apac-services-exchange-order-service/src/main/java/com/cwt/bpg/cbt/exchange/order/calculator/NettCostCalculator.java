package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;

public class NettCostCalculator extends CommonCalculator {

	public AirFeesBreakdown calculateFee(BigDecimal sellingPrice, Double commissionPct) {
		
		AirFeesBreakdown result = new AirFeesBreakdown();
		
		result.setNettCostInEO(safeValue(sellingPrice)
				.multiply(new BigDecimal(1).subtract(percentDecimal(commissionPct))));
		
		return result;
	}

}
