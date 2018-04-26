package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Service
public class OtherServiceFeesService {

	@Autowired
	@Qualifier(value="miscFeeCalculator")
	Calculator miscFeeCalculator;
	
	@Autowired
	@Qualifier(value="hkAirCalculator")
	Calculator hkAirCalculator;
	
	@Autowired
	@Qualifier(value="sgAirCalculator")
	Calculator airCalculator;
	
	@Autowired 
	MerchantFeeRepository merchantFeeRepo;

	public FeesBreakdown calculateMiscFee(OtherServiceFeesInput input) {
		return miscFeeCalculator.calculateFee(input, getMerchantFeePct(input));
	}
	
	public FeesBreakdown calculateAirFee(OtherServiceFeesInput input) {
		if(input.getCountryCode().equals("HK")) {
			return hkAirCalculator.calculateFee(input, getMerchantFeePct(input));
			
		}else if(input.getCountryCode().equals("SG") && 
				input.getCountryCode().equals("AU") && 
				input.getCountryCode().equals("NZ")) {
			return airCalculator.calculateFee(input, getMerchantFeePct(input));
		}else {
			return null;
		}
	}

	private MerchantFee getMerchantFeePct(OtherServiceFeesInput input) {
		
		MerchantFee merchantFee = merchantFeeRepo.getMerchantFee(
				input.getCountryCode(), 
				input.getClientType(), 
				input.getProductName());
		
		return merchantFee;
	}	
}
