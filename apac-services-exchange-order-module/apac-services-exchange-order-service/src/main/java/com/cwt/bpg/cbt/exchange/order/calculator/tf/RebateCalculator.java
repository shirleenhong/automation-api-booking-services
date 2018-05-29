package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class RebateCalculator extends FeeCalculator {

	@Override
    public BigDecimal getMfOnTf(TransactionFeesInput input, 
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

    @Override
    public BigDecimal getTotalCharge(
    		TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {
    	
        return safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(input.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));
    }
}
