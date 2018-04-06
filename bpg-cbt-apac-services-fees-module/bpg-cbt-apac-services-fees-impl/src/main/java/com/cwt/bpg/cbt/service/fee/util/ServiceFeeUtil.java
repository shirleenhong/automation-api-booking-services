package com.cwt.bpg.cbt.service.fee.util;

import java.math.BigDecimal;

public final class ServiceFeeUtil {
	

//	FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount
	public static BigDecimal fopAmount() {
		return new BigDecimal(0D);
	}
//	Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage
//		     Where: Charged Fare = Base Fare + Total Taxes
	public static BigDecimal calMerchantFeeAmount() {
		return new BigDecimal(0D);
	}
//	Transaction Fee Amount = Base Fare * Transaction Fee Percentage
	public static BigDecimal calTransactionFees() {
		return new BigDecimal(0D);
	}
//	Markup Amount = Base Fare * Mark Up Percentage
	public static BigDecimal calMarkupAmount() {
		return new BigDecimal(0D);
	}
//	Airline Commission/Commission Rebate Amount = Base Fare * Airline Commission/Commission Rebate Percentage
	public static BigDecimal calCommissionRebateAmount() {
		return new BigDecimal(0D);
	}
//	Fare Including Airline Taxes = (Base Fare + Taxes + OB Fee + Markup Amount) - Airline Commission Amount
	public static BigDecimal calFareWithAirlineTax(BigDecimal baseFare, 
			BigDecimal taxes, BigDecimal obFee, BigDecimal markupAmount, BigDecimal airlineCommissionAmount) {
		return new BigDecimal(0D);
	}
//	Total Amount = Fare Including Taxes + Transaction Fee + Merchant Fee + Fuel Surcharge + OB Fee
	public static BigDecimal calTotalAmount() {
		return new BigDecimal(0D);
	}
}
