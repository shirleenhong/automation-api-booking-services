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

		PriceBreakdown priceBreakdown = new PriceBreakdown();
		if(input.getNettFare() != null)
		{
			input.setBaseFare(input.getNettFare());
		}
		 
		
		
		BigDecimal transactionFeeAmount = roundAmount(calTransactionFeeAmount(input.getBaseFare(), input.getTransactionFeeAmount(), input.getTransactionFeePercentage()),input.getCountryCode());
		priceBreakdown.setTransactionFeeAmount(transactionFeeAmount);
		transactionFeeAmount = safeValue(transactionFeeAmount);
		
		BigDecimal markupAmount = roundAmount(calMarkupAmount(input.getBaseFare(), input.getMarkupAmount(), input.getMarkupPercentage()),input.getCountryCode());
		priceBreakdown.setMarkupAmount(markupAmount);
		markupAmount = safeValue(markupAmount);
		
		BigDecimal commissionRebateAmount = roundAmount(calCommissionRebateAmount(input.getBaseFare(), input.getCommissionRebateAmount(), input.getCommissionRebatePercentage()),input.getCountryCode());
		priceBreakdown.setCommissionRebateAmount(commissionRebateAmount);
		commissionRebateAmount = safeValue(commissionRebateAmount);
		
		BigDecimal fopAmount = roundAmount(calFopAmount(input.getBaseFare(), input.getTotalTaxes(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setFopAmount(fopAmount);
		fopAmount = safeValue(fopAmount);
		
		BigDecimal merchantFeeAmount = roundAmount(calMerchantFeeAmount(fopAmount, input.getMerchantFeeAmount(), input.getMerchantFeePercentage()),input.getCountryCode());
		priceBreakdown.setMerchantFeeAmount(merchantFeeAmount);
		merchantFeeAmount = safeValue(merchantFeeAmount);
		
		BigDecimal airFareWithTaxAmount = roundAmount(calFareWithAirlineTax(input.getBaseFare(), input.getTotalTaxes(), input.getObFee(), markupAmount, commissionRebateAmount),input.getCountryCode());
		priceBreakdown.setAirFareWithTaxAmount(airFareWithTaxAmount);
		airFareWithTaxAmount = safeValue(airFareWithTaxAmount);
		
		priceBreakdown.setTotalAmount(roundAmount(calTotalAmount(airFareWithTaxAmount, transactionFeeAmount, merchantFeeAmount, input.getFuelSurcharge()),input.getCountryCode()));
		
		return priceBreakdown;
	}
	
	private BigDecimal safeValue(BigDecimal value) {
		if(value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}
	
	private BigDecimal roundAmount(BigDecimal amount , String countryCode) {
		if(amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		// TODO: move precision to mongo config
		if (countryCode != null && (countryCode.equals(PriceCalculationInput.COUNTRY_CODE_INDIA) || countryCode.equals(PriceCalculationInput.COUNTRY_CODE_HONGKONG))) {
			return ServiceFeeUtil.round(amount, 0);
		}else {
			return ServiceFeeUtil.round(amount, 2);
		}
		
	}
}
