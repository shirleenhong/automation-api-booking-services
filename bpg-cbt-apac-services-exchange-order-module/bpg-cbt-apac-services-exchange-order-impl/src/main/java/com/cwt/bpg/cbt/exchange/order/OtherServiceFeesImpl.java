package com.cwt.bpg.cbt.exchange.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Service
public class OtherServiceFeesImpl implements OtherServiceFeesApi {

	@Autowired
	MiscFeeCalculator calculator;
	
	@Override
	public FeesBreakdown calculateMiscFee(OtherServiceFeesInput input) {
				
		//TODO From DB
		Double merchantFeePct = getMerchantFeePct();
		BigDecimal nettCost = getNettCost();
		
		return calculator.calMiscFee(input, merchantFeePct, nettCost);
	}

	private Double getMerchantFeePct() {
		
		return 2D;
	}	

	private BigDecimal getNettCost() {
		return BigDecimal.valueOf(2);
	}
}
