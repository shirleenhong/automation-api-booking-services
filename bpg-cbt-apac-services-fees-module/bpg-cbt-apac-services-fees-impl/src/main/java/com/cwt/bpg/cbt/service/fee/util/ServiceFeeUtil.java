package com.cwt.bpg.cbt.service.fee.util;

import java.math.BigDecimal;

public final class ServiceFeeUtil {
	

//	FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount
	public static BigDecimal calFopAmount(BigDecimal baseFare, BigDecimal totalTaxes, BigDecimal markupAmount, BigDecimal commissionRebateAmount) {
		return baseFare.add(totalTaxes).add(markupAmount).subtract(commissionRebateAmount);
	}
//	Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage
//		     Where: Charged Fare = Base Fare + Total Taxes
	public static BigDecimal calMerchantFeeAmount(BigDecimal fopAmount, BigDecimal merchantFeeAmountInput, Double merchantFeePercentage) {
		return merchantFeeAmountInput != null ? merchantFeeAmountInput : fopAmount.multiply(new BigDecimal(merchantFeePercentage)).divide(new BigDecimal(100));
	}
//	Transaction Fee Amount = Base Fare * Transaction Fee Percentage
	public static BigDecimal calTransactionFeeAmount(BigDecimal baseFare, BigDecimal transactionFeeAmountInput, Double transactionFeePercentage) {
		BigDecimal transactionFee = baseFare.multiply(new BigDecimal(transactionFeePercentage)).divide(new BigDecimal(100));
		return transactionFee.compareTo(transactionFeeAmountInput) == 1? transactionFee : transactionFeeAmountInput;
	}
//	Markup Amount = Base Fare * Mark Up Percentage
	public static BigDecimal calMarkupAmount(BigDecimal baseFare, BigDecimal markupAmountInput, Double markupPercentage) {
		return markupAmountInput != null ? markupAmountInput : baseFare.multiply(new BigDecimal(markupPercentage)).divide(new BigDecimal(100));
	}
//	Airline Commission/Commission Rebate Amount = Base Fare * Airline Commission/Commission Rebate Percentage
	public static BigDecimal calCommissionRebateAmount(BigDecimal baseFare, BigDecimal commissionRebateAmountInput, Double commissionRebatePercentage) {
		return commissionRebateAmountInput != null ? commissionRebateAmountInput : baseFare.multiply(new BigDecimal(commissionRebatePercentage)).divide(new BigDecimal(100));
	}
//	Fare Including Airline Taxes = (Base Fare + Taxes + OB Fee + Markup Amount) - Airline Commission Amount
	public static BigDecimal calFareWithAirlineTax(BigDecimal baseFare, 
			BigDecimal taxes, BigDecimal obFee, BigDecimal markupAmount, BigDecimal airlineCommissionAmount) {
		return baseFare.add(taxes).add(obFee).add(markupAmount).subtract(airlineCommissionAmount);
	}
//	Total Amount = Fare Including Taxes + Transaction Fee + Merchant Fee + Fuel Surcharge
	public static BigDecimal calTotalAmount(BigDecimal fareIncludingTaxes, BigDecimal transactionFee, BigDecimal merchantFee, BigDecimal fuelSurcharge) {
		return fareIncludingTaxes.add(transactionFee).add(merchantFee).add(fuelSurcharge);
	}
}
