package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.percentDecimal;
import static com.cwt.bpg.cbt.calculator.CalculatorUtils.safeValue;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;

@Component
public class NettCostCalculator {

	public AirFeesBreakdown calculateFee(BigDecimal sellingPrice, Double commissionPct) {

		AirFeesBreakdown result = new AirFeesBreakdown();
		
		result.setNettCost(safeValue(sellingPrice)
				.multiply(new BigDecimal(1).subtract(percentDecimal(commissionPct))));
		
		return result;
	}

}
