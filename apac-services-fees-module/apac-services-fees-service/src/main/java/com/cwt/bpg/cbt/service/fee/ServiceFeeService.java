package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import com.cwt.bpg.cbt.service.fee.util.ServiceFeeCalculator;

@Service
class ServiceFeeService {
	
	@Autowired 
	ServiceFeeCalculator c;
	
	PriceBreakdown calculate(PriceCalculationInput input) {

		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		} 
			
		BigDecimal transactionFeeAmount = c.round(c.calculateTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage()),input.getCountryCode());
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		transactionFeeAmount = c.safeValue(transactionFeeAmount);
		
		BigDecimal markupAmount = c.round(c.calculateMarkupAmount(input.getMarkupAmount(), input.getBaseFare(), input.getMarkupPercentage()),input.getCountryCode());
		priceBreakdown.setMarkupAmount(markupAmount);
		markupAmount = c.safeValue(markupAmount);
		
		BigDecimal commissionRebateAmount = c.round(c.calculateCommissionRebateAmount(input.getCommissionRebateAmount(), input.getBaseFare(), input.getCommissionRebatePercentage()),input.getCountryCode());
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		commissionRebateAmount = c.safeValue(commissionRebateAmount);
		
		BigDecimal fopAmount = c.round(c.calculateFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setFopAmount(fopAmount);
		fopAmount = c.safeValue(fopAmount);
		
		BigDecimal merchantFeeAmount = c.round(c.calculateMerchantFeeAmount(input.getMerchantFeeAmount(), fopAmount, input.getMerchantFeePercentage()),input.getCountryCode());
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		merchantFeeAmount = c.safeValue(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = c.round(c.calculateFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		airFareWithTaxAmount = c.safeValue(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(c.round(c.calculateTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()),input.getCountryCode()));
		
		return priceBreakdown;
	}	
}
