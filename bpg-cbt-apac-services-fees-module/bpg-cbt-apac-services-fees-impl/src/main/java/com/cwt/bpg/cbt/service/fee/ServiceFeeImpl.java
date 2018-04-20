package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import com.cwt.bpg.cbt.service.fee.util.ServiceFeeCalculator;

@Service
public class ServiceFeeImpl implements ServiceFeeApi {
	
	@Autowired 
	ServiceFeeCalculator c;
	
	@Override
	public PriceBreakdown calculate(PriceCalculationInput input) {

		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		} 
			
		BigDecimal transactionFeeAmount = c.roundAmount(c.calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage()),input.getCountryCode());
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		transactionFeeAmount = c.safeValue(transactionFeeAmount);
		
		BigDecimal markupAmount = c.roundAmount(c.calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage()),input.getCountryCode());
		priceBreakdown.setMarkupAmount(markupAmount);
		markupAmount = c.safeValue(markupAmount);
		
		BigDecimal commissionRebateAmount = c.roundAmount(c.calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage()),input.getCountryCode());
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		commissionRebateAmount = c.safeValue(commissionRebateAmount);
		
		BigDecimal fopAmount = c.roundAmount(c.calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setFopAmount(fopAmount);
		fopAmount = c.safeValue(fopAmount);
		
		BigDecimal merchantFeeAmount = c.roundAmount(c.calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage()),input.getCountryCode());
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		merchantFeeAmount = c.safeValue(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = c.roundAmount(c.calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		airFareWithTaxAmount = c.safeValue(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(c.roundAmount(c.calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()),input.getCountryCode()));
		
		return priceBreakdown;
	}	
}
