package com.cwt.bpg.cbt.service.fee.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeCalculator {

	@Autowired
	private ScaleConfig scaleConfig;	
	
	public PriceBreakdown calculateFee(PriceCalculationInput input) {

		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		} 
		
		int scale = scaleConfig.getScale(input.getCountryCode());
			
		BigDecimal transactionFeeAmount = round(calculateTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage()), scale);
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		transactionFeeAmount = safeValue(transactionFeeAmount);
		
		BigDecimal markupAmount = round(calculateMarkupAmount(input.getMarkupAmount(), input.getBaseFare(), input.getMarkupPercentage()), scale);
		priceBreakdown.setMarkupAmount(markupAmount);
		markupAmount = safeValue(markupAmount);
		
		BigDecimal commissionRebateAmount = round(calculateCommissionRebateAmount(input.getCommissionRebateAmount(), input.getBaseFare(), input.getCommissionRebatePercentage()), scale);
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		commissionRebateAmount = safeValue(commissionRebateAmount);
		
		BigDecimal fopAmount = round(calculateFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount), scale);
		priceBreakdown.setFopAmount(fopAmount);
		fopAmount = safeValue(fopAmount);
		
		BigDecimal merchantFeeAmount = round(calculateMerchantFeeAmount(input.getMerchantFeeAmount(), fopAmount, input.getMerchantFeePercentage()), scale);
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		merchantFeeAmount = safeValue(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = round(calculateFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount), scale);
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		airFareWithTaxAmount = safeValue(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(round(calculateTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()), scale));
		
		return priceBreakdown;
	}	
	
	/**
	 * FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount
	 * @param baseFare Base Fare
	 * @param totalTaxes Taxes
	 * @param markupAmount Markup Amount
	 * @param commissionRebateAmount Commission Rebate Amount
	 * @return Computed FOP Amount
	 */
	public BigDecimal calculateFopAmount(BigDecimal baseFare, BigDecimal totalTaxes, BigDecimal markupAmount, BigDecimal commissionRebateAmount) {
		return baseFare.add(totalTaxes).add(markupAmount).subtract(commissionRebateAmount);
	}

	/**
	 * Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage
	 * Where: Charged Fare = Base Fare + Total Taxes
	 * @param merchantFeeAmountInput Merchant Fee Amount
	 * @param fopAmount FOP Amount
	 * @param merchantFeePercentage Merchant Fee Percentage of FOP Amount
	 * @return Merchant Fee Amount input if it's given. Otherwise, computed Merchant Fee Amount based on given percentage
	 */
	public BigDecimal calculateMerchantFeeAmount(BigDecimal merchantFeeAmountInput, BigDecimal fopAmount, Double merchantFeePercentage) {
		return merchantFeeAmountInput != null ? merchantFeeAmountInput : calculatePercentage(fopAmount, merchantFeePercentage);
	}

	/**
	 * Transaction Fee Amount = Base Fare * Transaction Fee Percentage
	 * @param baseFare Base Fare
	 * @param transactionFeeAmountInput Transaction Fee Amount
	 * @param transactionFeePercentage Transaction Fee Percentage
	 * @return Transaction Fee Amount input if it's given. Otherwise, computed Transaction Fee Amount based on given percentage
	 */
	public BigDecimal calculateTransactionFeeAmount(BigDecimal baseFare, BigDecimal transactionFeeAmountInput, Double transactionFeePercentage) {
	    BigDecimal transactionFee = calculatePercentage(baseFare, transactionFeePercentage);
	    if (transactionFeeAmountInput == null) {
	        return transactionFee;
        }
        else {
            return transactionFeePercentage != null && transactionFee.compareTo(transactionFeeAmountInput) < 0 ? transactionFee : safeValue(transactionFeeAmountInput);
        }
	}

	/**
	 * Markup Amount = Base Fare * Mark Up Percentage
	 * @param markupAmountInput Markup Amount
	 * @param baseFare Base Fare
	 * @param markupPercentage Markup Percentage
	 * @return Markup Amount input if it's given. Otherwise, computed Markup Amount based on given percentage
	 */
	public BigDecimal calculateMarkupAmount(BigDecimal markupAmountInput, BigDecimal baseFare, Double markupPercentage) {
		return markupAmountInput != null ? markupAmountInput : calculatePercentage(baseFare, markupPercentage);
	}

	/**
	 * Airline Commission/Commission Rebate Amount = Base Fare * Airline Commission/Commission Rebate Percentage
	 * @param commissionRebateAmountInput Commission Rebate Amount
	 * @param baseFare Base Fare
	 * @param commissionRebatePercentage Commission Rebate Percentage
	 * @return Commission Rebate Amount input if it's given. Otherwise, Commission Rebate Amount based on given percentage
	 */
	public BigDecimal calculateCommissionRebateAmount(BigDecimal commissionRebateAmountInput, BigDecimal baseFare, Double commissionRebatePercentage) {
		return commissionRebateAmountInput != null ? commissionRebateAmountInput : calculatePercentage(baseFare, commissionRebatePercentage);
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
	public BigDecimal calculateFareWithAirlineTax(BigDecimal baseFare, BigDecimal taxes, BigDecimal obFee, BigDecimal markupAmount, BigDecimal airlineCommissionAmount) {
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
	public BigDecimal calculateTotalAmount(BigDecimal fareIncludingTaxes,
			BigDecimal transactionFee, BigDecimal merchantFee, BigDecimal fuelSurcharge) {
		return fareIncludingTaxes.add(transactionFee).add(merchantFee).add(fuelSurcharge);
	}
}
