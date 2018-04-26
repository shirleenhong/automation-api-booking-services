package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.calculator.config.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.HkBspAirCalculator;

public class OtherServiceCalculatorFactory {
	
	@Autowired
	@Qualifier("hkBspAirCalculator")
	private HkBspAirCalculator hkBspAirCalculator;
	
	private Map<String, Calculator> serviceMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		
		this.serviceMap.put(Country.HONG_KONG.getCode(), hkBspAirCalculator);
	}
	
	public Calculator getCalculator(String countryCode) {
		
		if (this.serviceMap.containsKey(countryCode)) {
			return this.serviceMap.get(countryCode);
		}
		else {
			
			throw new IllegalArgumentException("Country not supported");
		}
	}

}
