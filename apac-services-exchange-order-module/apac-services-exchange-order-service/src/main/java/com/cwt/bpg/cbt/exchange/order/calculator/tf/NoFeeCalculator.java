package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class NoFeeCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalDiscount(int tripType,
			TransactionFeesBreakdown breakdown) {
        return null;
    }

	@Override
    public BigDecimal getTotalOrCom(int tripType,
			TransactionFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getTotalOrCom2(int tripType,
			TransactionFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getMerchantFee(
    		TransactionFeesInput input,
			TransactionFeesBreakdown breakdown
			) {
        return calculatePercentage(
        		safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getTotalTaxes()))
        		.add(safeValue(breakdown.getTotalGst())),
        			input.getMerchantFeePercent());
    }

	@Override
    public BigDecimal getMfOnTf(int tripType,
			TransactionFeesInput input,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

	@Override
    public BigDecimal getTotalFee() {
        return null;
    }

	@Override
    public BigDecimal getTotalSellingFare(TransactionFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalGst()))
    			.add(safeValue(breakdown.getTotalMerchantFee()));
    }

	@Override
    public BigDecimal getTotalCharge(
    		TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalTaxes()));
    }
}
