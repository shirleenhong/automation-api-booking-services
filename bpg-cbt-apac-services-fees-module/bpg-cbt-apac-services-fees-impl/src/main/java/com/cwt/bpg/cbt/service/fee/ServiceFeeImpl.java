package com.cwt.bpg.cbt.service.fee;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;
import com.cwt.bpg.cbt.service.fee.util.ServiceFeeUtil;

import static com.cwt.bpg.cbt.service.fee.util.ServiceFeeUtil.*;

@Service
public class ServiceFeeImpl implements ServiceFeeApi {
	
	@Override
	public PriceBreakdown calculate(PriceCalculationInput input) {
		
		input.setBaseFare(input.getBaseFare()!=null? input.getBaseFare():BigDecimal.ZERO);
		input.setTotalTaxes(input.getTotalTaxes()!=null? input.getTotalTaxes():BigDecimal.ZERO);
//		input.setMarkupPercentage(input.getMarkupPercentage()!=null? input.getMarkupPercentage():0D);
//		input.setCommissionRebatePercentage(input.getCommissionRebatePercentage()!=null? input.getCommissionRebatePercentage():0D);
//		input.setMerchantFeePercentage(input.getMerchantFeePercentage()!=null? input.getMerchantFeePercentage():0D);
		input.setObFee(input.getObFee()!=null? input.getObFee():BigDecimal.ZERO);
		input.setFuelSurcharge(input.getFuelSurcharge()!=null? input.getFuelSurcharge():BigDecimal.ZERO);
		
		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		}
		 
		
		
		BigDecimal transactionFeeAmount = calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage());
		priceBreakdown.setTransactionFeeAmount(roundAmount(transactionFeeAmount,input.getCountryCode()));
		
		BigDecimal markupAmount = calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage());
		priceBreakdown.setMarkupAmount(roundAmount(markupAmount,input.getCountryCode()));
		
		BigDecimal commissionRebateAmount = calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage());
		priceBreakdown.setCommissionRebateAmount(roundAmount(commissionRebateAmount,input.getCountryCode()));
		
		BigDecimal fopAmount = calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount);
		priceBreakdown.setFopAmount(roundAmount(fopAmount,input.getCountryCode()));
		
		BigDecimal merchantFeeAmount = calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage());
		priceBreakdown.setMerchantFeeAmount(roundAmount(merchantFeeAmount,input.getCountryCode()));
		
		BigDecimal airFareWithTaxAmount = calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount);
		priceBreakdown.setAirFareWithTaxAmount(roundAmount(airFareWithTaxAmount,input.getCountryCode()));
		
		priceBreakdown.setTotalAmount(roundAmount(calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()),input.getCountryCode()));
		
		return priceBreakdown;
	}
	
	private BigDecimal roundAmount(BigDecimal amount , String countryCode) {
		if(amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		if (countryCode != null && (countryCode.equals(PriceCalculationInput.COUNTRY_CODE_INDIA) || countryCode.equals(PriceCalculationInput.COUNTRY_CODE_HONGKONG))) {
			return ServiceFeeUtil.round(amount, 0);
		}else {
			return ServiceFeeUtil.round(amount, 2);
		}
		
	}
}
