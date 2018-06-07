package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component("tfRebateCalculator")
public class RebateCalculator extends FeeCalculator {

	@Override
    public BigDecimal getMfOnTf(IndiaAirFeesInput input,
    		IndiaAirFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

    @Override
    public BigDecimal getTotalCharge(
			IndiaAirFeesBreakdown breakdown) {
    	
        return safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));
    }
}
