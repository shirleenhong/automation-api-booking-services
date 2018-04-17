package com.cwt.bpg.cbt.service.fee.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class ServiceFeeUtil {

	/**
	 * FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount 
	 * @param baseFare
	 * @param totalTaxes
	 * @param markupAmount
	 * @param commissionRebateAmount
	 * @return
	 */
	public static BigDecimal calFopAmount(BigDecimal baseFare, BigDecimal totalTaxes, BigDecimal markupAmount, BigDecimal commissionRebateAmount) {
		return baseFare.add(totalTaxes).add(markupAmount).subtract(commissionRebateAmount);
	}
	
	/**
	 * Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage
	 * Where: Charged Fare = Base Fare + Total Taxes 
	 * @param fopAmount
	 * @param merchantFeeAmountInput
	 * @param merchantFeePercentage
	 * @return
	 */
	public static BigDecimal calMerchantFeeAmount(BigDecimal fopAmount, BigDecimal merchantFeeAmountInput, Double merchantFeePercentage) {
		return getPercentageAmount(fopAmount, merchantFeeAmountInput, merchantFeePercentage);
	}

	/**
	 * Transaction Fee Amount = Base Fare * Transaction Fee Percentage 
	 * @param baseFare
	 * @param transactionFeeAmountInput
	 * @param transactionFeePercentage
	 * @return
	 */
	public static BigDecimal calTransactionFeeAmount(BigDecimal baseFare, BigDecimal transactionFeeAmountInput, Double transactionFeePercentage) {
		if(transactionFeePercentage != null) {
			BigDecimal transactionFee = baseFare.multiply(new BigDecimal(transactionFeePercentage)).divide(new BigDecimal(100));
			return transactionFeeAmountInput == null || transactionFee.compareTo(transactionFeeAmountInput) == -1? transactionFee : transactionFeeAmountInput;
		}else if(transactionFeeAmountInput != null) {
			return transactionFeeAmountInput;
		}
		return new BigDecimal(0);
		
	}

	/**
	 * Markup Amount = Base Fare * Mark Up Percentage 
	 * @param baseFare
	 * @param markupAmountInput
	 * @param markupPercentage
	 * @return
	 */
	public static BigDecimal calMarkupAmount(BigDecimal baseFare, BigDecimal markupAmountInput, Double markupPercentage) {
		return getPercentageAmount(baseFare, markupAmountInput, markupPercentage);
	}
	
	/**
	 * Airline Commission/Commission Rebate Amount = Base Fare * Airline Commission/Commission Rebate Percentage 
	 * @param baseFare
	 * @param commissionRebateAmountInput
	 * @param commissionRebatePercentage
	 * @return
	 */
	public static BigDecimal calCommissionRebateAmount(BigDecimal baseFare, BigDecimal commissionRebateAmountInput, Double commissionRebatePercentage) {
		return getPercentageAmount(baseFare, commissionRebateAmountInput, commissionRebatePercentage);
	}

	/**
	 * Fare Including Airline Taxes = (Base Fare + Taxes + OB Fee + Markup Amount) - Airline Commission Amount
	 * @param baseFare
	 * @param taxes
	 * @param obFee
	 * @param markupAmount
	 * @param airlineCommissionAmount
	 * @return
	 */
	public static BigDecimal calFareWithAirlineTax(BigDecimal baseFare, 
			BigDecimal taxes, BigDecimal obFee, BigDecimal markupAmount, BigDecimal airlineCommissionAmount) {
		return baseFare.add(taxes).add(obFee).add(markupAmount).subtract(airlineCommissionAmount);
	}

	/**
	 * Total Amount = Fare Including Taxes + Transaction Fee + Merchant Fee + Fuel Surcharge 
	 * @param fareIncludingTaxes
	 * @param transactionFee
	 * @param merchantFee
	 * @param fuelSurcharge
	 * @return
	 */
	public static BigDecimal calTotalAmount(BigDecimal fareIncludingTaxes, BigDecimal transactionFee, BigDecimal merchantFee, BigDecimal fuelSurcharge) {
		return fareIncludingTaxes.add(transactionFee).add(merchantFee).add(fuelSurcharge);
	}

	public static BigDecimal round(BigDecimal d, int scale) {
		return d.setScale(scale, RoundingMode.HALF_UP);
	}
	
	private static BigDecimal getPercentageAmount(BigDecimal baseAmount, BigDecimal amountInput, Double percentage) {
		return amountInput != null ? amountInput : baseAmount.multiply(new BigDecimal(percentage, MathContext.DECIMAL64)).divide(new BigDecimal(100));
	}
}
