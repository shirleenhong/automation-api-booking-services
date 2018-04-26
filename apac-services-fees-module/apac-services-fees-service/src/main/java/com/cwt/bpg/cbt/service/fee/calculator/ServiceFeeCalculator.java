package com.cwt.bpg.cbt.service.fee.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeCalculator extends CommonCalculator {

//	@Autowired
//	Environment env;
	
	@Autowired
	ScaleConfig scaleConfig;
	
	public PriceBreakdown calculateFee(PriceCalculationInput input) {
		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		} 
			
		//ServiceFeeCalculator c = factory.getCalculator(input.getCountryCode());
		//setCountryCode(input.getCountryCode());
		int scale = scaleConfig.getScale(input.getCountryCode());
		
		BigDecimal transactionFeeAmount = round(calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage()), scale);
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		transactionFeeAmount = safeValue(transactionFeeAmount);
		
		BigDecimal markupAmount = round(calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage()), scale);
		priceBreakdown.setMarkupAmount(markupAmount);
		markupAmount = safeValue(markupAmount);
		
		BigDecimal commissionRebateAmount = round(calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage()), scale);
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		commissionRebateAmount = safeValue(commissionRebateAmount);
		
		BigDecimal fopAmount = round(calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount), scale);
		priceBreakdown.setFopAmount(fopAmount);
		fopAmount = safeValue(fopAmount);
		
		BigDecimal merchantFeeAmount = round(calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage()), scale);
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		merchantFeeAmount = safeValue(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = round(calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount), scale);
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		airFareWithTaxAmount = safeValue(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(round(calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()), scale));
		
		return priceBreakdown;
		
	}
	
	//@A
	
	//public String countryCode;

	/**
	 * FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount 
	 * @param baseFare Base Fare
	 * @param totalTaxes Taxes
	 * @param markupAmount Markup Amount
	 * @param commissionRebateAmount Commission Rebate Amount
	 * @return Computed FOP Amount
	 */
	//TODO Should be private
	public BigDecimal calFopAmount(BigDecimal baseFare, BigDecimal totalTaxes, BigDecimal markupAmount, BigDecimal commissionRebateAmount) {
		return baseFare.add(totalTaxes).add(markupAmount).subtract(commissionRebateAmount);
	}
	
	/**
	 * Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage
	 * Where: Charged Fare = Base Fare + Total Taxes 
	 * @param fopAmount FOP Amount
	 * @param merchantFeeAmountInput Merchant Fee Amount
	 * @param merchantFeePercentage Merchant Fee Percentage of FOP Amount
	 * @return Merchant Fee Amount input if it's given. Otherwise, computed Merchant Fee Amount based on given percentage
	 */
	public BigDecimal calMerchantFeeAmount(BigDecimal fopAmount, BigDecimal merchantFeeAmountInput, Double merchantFeePercentage) {
		return getPercentageAmount(fopAmount, merchantFeeAmountInput, merchantFeePercentage);
	}

	/**
	 * Transaction Fee Amount = Base Fare * Transaction Fee Percentage 
	 * @param baseFare Base Fare
	 * @param transactionFeeAmountInput Transaction Fee Amount
	 * @param transactionFeePercentage Transaction Fee Percentage
	 * @return Transaction Fee Amount input if it's given. Otherwise, computed Transaction Fee Amount based on given percentage
	 */
	public BigDecimal calTransactionFeeAmount(BigDecimal baseFare, BigDecimal transactionFeeAmountInput, Double transactionFeePercentage) {
		return transfactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		
	}

	public BigDecimal transfactionFeeAmount(BigDecimal baseFare, BigDecimal transactionFeeAmountInput,
			Double transactionFeePercentage) {
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
	 * @param baseFare Base Fare
	 * @param markupAmountInput Markup Amount
	 * @param markupPercentage Markup Percentage
	 * @return Markup Amount input if it's given. Otherwise, computed Markup Amount based on given percentage
	 */
	public BigDecimal calMarkupAmount(BigDecimal baseFare, BigDecimal markupAmountInput, Double markupPercentage) {
		return getPercentageAmount(baseFare, markupAmountInput, markupPercentage);
	}
	
	/**
	 * Airline Commission/Commission Rebate Amount = Base Fare * Airline Commission/Commission Rebate Percentage 
	 * @param baseFare Base Fare
	 * @param commissionRebateAmountInput Commission Rebate Amount
	 * @param commissionRebatePercentage Commission Rebate Percentage
	 * @return Commission Rebate Amount input if it's given. Otherwise, Commission Rebate Amount based on given percentage
	 */
	public BigDecimal calCommissionRebateAmount(BigDecimal baseFare, BigDecimal commissionRebateAmountInput, Double commissionRebatePercentage) {
		return getPercentageAmount(baseFare, commissionRebateAmountInput, commissionRebatePercentage);
	}

	/**
	 * Fare Including Airline Taxes = (Base Fare + Taxes + OB Fee + Markup Amount) - Airline Commission Amount
	 * @param baseFare Base Fare
	 * @param taxes Taxes
	 * @param obFee OB Fee
	 * @param markupAmount Markup Amount
	 * @param airlineCommissionAmount Airline Commission Amount
	 * @return Fare including Airline Taxes
	 */
	public BigDecimal calFareWithAirlineTax(BigDecimal baseFare, 
			BigDecimal taxes, BigDecimal obFee, BigDecimal markupAmount, BigDecimal airlineCommissionAmount) {
		return baseFare.add(taxes).add(obFee).add(markupAmount).subtract(airlineCommissionAmount);
	}

	/**
	 * Total Amount = Fare Including Taxes + Transaction Fee + Merchant Fee + Fuel Surcharge 
	 * @param fareIncludingTaxes Fare including Taxes
	 * @param transactionFee Transaction Fee
	 * @param merchantFee Merchant Fee
	 * @param fuelSurcharge Fuel Surcharge
	 * @return Total Amount
	 */
	public BigDecimal calTotalAmount(BigDecimal fareIncludingTaxes, BigDecimal transactionFee, BigDecimal merchantFee, BigDecimal fuelSurcharge) {
		return fareIncludingTaxes.add(transactionFee).add(merchantFee).add(fuelSurcharge);
	}
	
//	public int getScale(String countryCode) {
//		Integer scaleProp = env.getProperty("com.cwt.bpg.cbt.calc.scale.".concat(countryCode), Integer.class);
//		return scaleProp != null ? scaleProp.intValue() : 0 ;		
//	}

//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;		
//	}
//
//	@Override
//	public int getScale() {
//		Integer scaleProp = env.getProperty("com.cwt.bpg.cbt.calc.scale.".concat(countryCode), Integer.class);
//		return scaleProp != null ? scaleProp.intValue() : 0 ;
//	}
}
