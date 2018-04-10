package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import static com.cwt.bpg.cbt.service.fee.util.ServiceFeeUtil.*;

@Service
public class ServiceFeeImpl implements ServiceFeeApi {
	
	@Override
	public PriceBreakdown calculate(PriceCalculationInput input) {
		
		
		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		}
		
		BigDecimal transactionFeeAmount = calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage());
		priceBreakdown.setTransactionFeeAmount(round(transactionFeeAmount));
		
		BigDecimal markupAmount = calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage());
		priceBreakdown.setMarkupAmount(round(markupAmount));
		
		BigDecimal commissionRebateAmount = calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage());
		priceBreakdown.setCommissionRebateAmount(round(commissionRebateAmount));
		
		BigDecimal fopAmount = calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount);
		priceBreakdown.setFopAmount(round(fopAmount));
		
		BigDecimal merchantFeeAmount = calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage());
		priceBreakdown.setMerchantFeeAmount(round(merchantFeeAmount));
		
		BigDecimal airFareWithTaxAmount = calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount);
		priceBreakdown.setAirFareWithTaxAmount(round(airFareWithTaxAmount));
		
		priceBreakdown.setTotalAmount(round(calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge())));
		
		
		
		return priceBreakdown;
	}
}
