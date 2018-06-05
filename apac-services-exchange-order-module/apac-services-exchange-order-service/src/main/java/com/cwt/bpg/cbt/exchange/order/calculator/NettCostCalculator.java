package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesBreakdown;

@Component
public class NettCostCalculator extends CommonCalculator {

	public AirFeesBreakdown calculateFee(BigDecimal sellingPrice, Double commissionPct) {

		HkSgAirFeesBreakdown result = new HkSgAirFeesBreakdown();
		
		result.setNettCost(safeValue(sellingPrice)
				.multiply(new BigDecimal(1).subtract(percentDecimal(commissionPct))));
		
		return result;
	}

}
