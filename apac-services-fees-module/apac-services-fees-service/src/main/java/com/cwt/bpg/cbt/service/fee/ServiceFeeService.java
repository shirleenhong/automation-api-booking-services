package com.cwt.bpg.cbt.service.fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.service.fee.calculator.ServiceFeeCalculator;
import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

@Service
public class ServiceFeeService {
		
	@Autowired
	private ServiceFeeCalculator calculator;
	
	public PriceBreakdown calculate(PriceCalculationInput input) {

		return calculator.calculateFee(input);
		
	}
}
