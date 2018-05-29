package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

public class FeeCalculator extends CommonCalculator {

	public FeesBreakdown calculate(TransactionFeesInput genericInput) {
		
		return new FeesBreakdown();
	}
	
	public BigDecimal getTotalAirCom(TransactionFeesInput input) {
		return safeValue(input.getBaseFare())
					.add(safeValue(input.getYqTax()))
					.add(safeValue(input.getAirlineCommission()));
	}
	
	public BigDecimal getTotalMarkUp() {
		return null;
	}
	
	public BigDecimal getTotalDiscount(int tripType,
			TransactionFeesBreakdown breakdown) {
		
		BigDecimal commissionAmount = calculatePercentage(
					safeValue(breakdown.getTotalIATACom()), 
				breakdown.getClientDiscountPercent());
		
		if(tripType == TripTypes.INT.getId()) {
			return commissionAmount.add(safeValue(breakdown.getTotalReturnableOr()));
		}
		
		return commissionAmount;
	}
	
	public BigDecimal getTotalFare(TransactionFeesInput input) {
		return safeValue(input.getBaseFare());
	}
	
	public BigDecimal getTotalOrCom(int tripType, 
			TransactionFeesInput input) {
		
		if(tripType == TripTypes.INT.getId()) {
			calculatePercentage(calculatePercentage(
					safeValue(input.getBaseFare())
						.subtract(input.getAirlineCommission()),
							input.getAirlineCommisionPercent()), 
					input.getReturnOrCommissionPercent());
		}
		
		return null;

	}

	public BigDecimal getTotalOrCom2(int tripType, 
			TransactionFeesInput input) {
		
		if(tripType == TripTypes.INT.getId()) {
			calculatePercentage(input.getAirlineOrCommission(), 
					input.getReturnOrCommissionPercent());
		}
		
		return null;
	}
	
	public BigDecimal getMerchantFee(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown
			) {
		
		return calculatePercentage(
				safeValue(breakdown.getTotalSellFare())
				.subtract(breakdown.getTotalDiscount())
				.add(breakdown.getTotalTaxes())
				.add(breakdown.getTotalGst()), input.getMerchantFeePercent());
	}
	
	public BigDecimal getMfOnTf(int tripType, 
			TransactionFeesInput input, 
			BigDecimal totalGstOnTf) {

		if(tripType == TripTypes.INT.getId()) {
			return calculatePercentage(safeValue(input.getFee()).add(totalGstOnTf), 
					input.getMerchantFeePercent());
		}
		
		return calculatePercentage(input.getFee(), 
				input.getMerchantFeePercent());
	}
	
	public BigDecimal getTotalNettFare(TransactionFeesInput input) {
		return safeValue(input.getBaseFare());		
	}
	
	public BigDecimal getTotalFee() {
		return null;		
	}
	
	public BigDecimal getTotalSellingFare(TransactionFeesBreakdown breakdown) {

		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getTotalDiscount()))
				.add(safeValue(breakdown.getTotalGst()))
				.add(safeValue(breakdown.getTotalMerchantFee()));		
	}
	
	public BigDecimal getTotalCharge(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {
		
		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getMerchantFeeOnTF()))
				.add(safeValue(input.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));	
	}
	
	public Boolean getDdlFeeApply() {
		return Boolean.TRUE;
	}

}
