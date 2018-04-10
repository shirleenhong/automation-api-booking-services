package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import com.cwt.bpg.cbt.service.fee.util.ServiceFeeUtil;

@Service
public class ServiceFeeImpl implements ServiceFeeApi {
	
	@Override
	public PriceBreakdown calculate(PriceCalculationInput input) {
		
		input.setBaseFare(input.getBaseFare()!=null? input.getBaseFare():BigDecimal.ZERO);
		input.setTotalTaxes(input.getTotalTaxes()!=null? input.getTotalTaxes():BigDecimal.ZERO);
		input.setMarkupPercentage(input.getMarkupPercentage()!=null? input.getMarkupPercentage():0D);
		input.setMarkupAmount(input.getMarkupAmount()!=null? input.getMarkupAmount():BigDecimal.ZERO);
		input.setCommissionRebateAmount(input.getCommissionRebateAmount()!=null? input.getCommissionRebateAmount():BigDecimal.ZERO);
		input.setCommissionRebatePercentage(input.getCommissionRebatePercentage()!=null? input.getCommissionRebatePercentage():0D);
		input.setMerchantFeeAmount(input.getMerchantFeeAmount()!=null? input.getMerchantFeeAmount():BigDecimal.ZERO);
		input.setMerchantFeePercentage(input.getMerchantFeePercentage()!=null? input.getMerchantFeePercentage():0D);
		input.setTransactionFeeAmount(input.getTransactionFeeAmount()!=null? input.getTransactionFeeAmount():BigDecimal.ZERO);
		input.setTransactionFeePercentage(input.getTransactionFeePercentage()!=null? input.getTransactionFeePercentage():0D);
		input.setObFee(input.getObFee()!=null? input.getObFee():BigDecimal.ZERO);
		input.setFuelSurcharge(input.getFuelSurcharge()!=null? input.getFuelSurcharge():BigDecimal.ZERO);
		
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
