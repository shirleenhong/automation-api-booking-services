package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Service
public class OtherServiceFeesService implements OtherServiceFeesApi {

	@Autowired
	MiscFeeCalculator calculator;
	
	@Autowired 
	MerchantFeeApi merchantFeeApi;
	
	@Override
	public FeesBreakdown calculateMiscFee(OtherServiceFeesInput input) {
		return calculator.calMiscFee(input, getMerchantFeePct(input));
	}

	private Double getMerchantFeePct(OtherServiceFeesInput input) {
		
		MerchantFee merchantFeePct = merchantFeeApi.getMerchantFee(
				input.getCountryCode(), 
				input.getClientType(), 
				input.getProductName());
		
		if(merchantFeePct != null) 
		{
			return merchantFeePct.getMerchantFeePct();
		}
		
		return 0D;
	}	
}
