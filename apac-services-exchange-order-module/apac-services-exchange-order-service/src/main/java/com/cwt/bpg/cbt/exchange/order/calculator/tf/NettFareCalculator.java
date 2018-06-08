package com.cwt.bpg.cbt.exchange.order.calculator.tf;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component("tfNettFareCalculator")
public class NettFareCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).subtract(safeValue(breakdown.getTotalAirlineCommission()))
				.subtract(safeValue(breakdown.getTotalOverheadCommission()));
	}
}
