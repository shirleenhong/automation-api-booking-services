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
		
		input.setBaseFare(input.getBaseFare()!=null? input.getBaseFare():BigDecimal.ZERO);
		input.setTotalTaxes(input.getTotalTaxes()!=null? input.getTotalTaxes():BigDecimal.ZERO);
		input.setMarkupPercentage(input.getMarkupPercentage()!=null? input.getMarkupPercentage():100D);
		//input.setMarkupAmount(input.getMarkupAmount()!=null? input.getMarkupAmount():BigDecimal.ZERO);
		//input.setCommissionRebateAmount(input.getCommissionRebateAmount()!=null? input.getCommissionRebateAmount():BigDecimal.ZERO);
		input.setCommissionRebatePercentage(input.getCommissionRebatePercentage()!=null? input.getCommissionRebatePercentage():100D);
		//input.setMerchantFeeAmount(input.getMerchantFeeAmount()!=null? input.getMerchantFeeAmount():BigDecimal.ZERO);
		input.setMerchantFeePercentage(input.getMerchantFeePercentage()!=null? input.getMerchantFeePercentage():100D);
		//input.setTransactionFeeAmount(input.getTransactionFeeAmount()!=null? input.getTransactionFeeAmount():BigDecimal.ZERO);
		input.setTransactionFeePercentage(input.getTransactionFeePercentage()!=null? input.getTransactionFeePercentage():100D);
		input.setObFee(input.getObFee()!=null? input.getObFee():BigDecimal.ZERO);
		input.setFuelSurcharge(input.getFuelSurcharge()!=null? input.getFuelSurcharge():BigDecimal.ZERO);
		
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
