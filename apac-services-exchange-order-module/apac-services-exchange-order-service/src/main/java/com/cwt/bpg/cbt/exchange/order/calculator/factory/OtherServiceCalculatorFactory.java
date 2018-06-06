package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesInput;

public class OtherServiceCalculatorFactory {
	
	@Autowired
	@Qualifier("hkAirCalculator")
	private Calculator<AirFeesBreakdown, HkSgAirFeesInput> hkAirCalculator;
	
	@Autowired
	@Qualifier("sgAirCalculator")
	private Calculator<AirFeesBreakdown, HkSgAirFeesInput> sgAirCalculator;
		
	private Map<String, Calculator<AirFeesBreakdown, HkSgAirFeesInput>> calculatorMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		
		calculatorMap.put(Country.HONG_KONG.getCode(), hkAirCalculator);
		calculatorMap.put(Country.SINGAPORE.getCode(), sgAirCalculator);
		calculatorMap.put(Country.AUSTRALIA.getCode(), sgAirCalculator);
		calculatorMap.put(Country.NEW_ZEALAND.getCode(), sgAirCalculator);
	}
	
	public Calculator<AirFeesBreakdown, HkSgAirFeesInput> getCalculator(String countryCode) {
		
		if (calculatorMap.containsKey(countryCode)) {
			return calculatorMap.get(countryCode);
		}
		else {
			throw new IllegalArgumentException("Country not supported");
		}
	}

}
