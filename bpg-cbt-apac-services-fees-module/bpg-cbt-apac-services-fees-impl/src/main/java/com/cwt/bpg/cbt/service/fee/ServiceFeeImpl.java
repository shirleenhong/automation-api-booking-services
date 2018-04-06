package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import com.cwt.bpg.cbt.service.fee.util.ServiceFeeUtil;

public class ServiceFeeImpl implements ServiceFeeApi {
	
	@Override
	public PriceBreakdown calculate(PriceCalculationInput input) {
		
		
		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		}
		
		BigDecimal transactionFeeAmount = ServiceFeeUtil.calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage());
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		
		BigDecimal markupAmount = ServiceFeeUtil.calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage());
		priceBreakdown.setMarkupAmount(markupAmount);
		
		BigDecimal commissionRebateAmount = ServiceFeeUtil.calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage());
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		
		BigDecimal fopAmount = ServiceFeeUtil.calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount);
		priceBreakdown.setFopAmount(fopAmount);
		
		BigDecimal merchantFeeAmount = ServiceFeeUtil.calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage());
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = ServiceFeeUtil.calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount);
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(ServiceFeeUtil.calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()));
		
		return priceBreakdown;
	}
}
