package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.calculator.BspAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Service
public class OtherServiceFeesService {

	@Autowired
	MiscFeeCalculator miscFeeCalculator;
	
	@Autowired
	BspAirCalculator bspAirCalculator;
	
	@Autowired 
	MerchantFeeRepository merchantFeeRepo;

	public FeesBreakdown calculateMiscFee(OtherServiceFeesInput input) {
		return miscFeeCalculator.calMiscFee(input, getMerchantFeePct(input));
	}

	public FeesBreakdown calculateBspAirFee(OtherServiceFeesInput input) {
		return bspAirCalculator.calBspAirFee(input, getMerchantFeePct(input));
	}

	private Double getMerchantFeePct(OtherServiceFeesInput input) {
		
		MerchantFee merchantFeePct = merchantFeeRepo.getMerchantFee(
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
