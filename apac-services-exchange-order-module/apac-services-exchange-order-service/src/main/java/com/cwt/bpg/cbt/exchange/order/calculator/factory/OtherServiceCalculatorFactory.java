package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;

public class OtherServiceCalculatorFactory {
	
	@Autowired
	@Qualifier("hkAirCalculator")
	private Calculator hkAirCalculator;
	
	@Autowired
	@Qualifier("sgAirCalculator")
	private Calculator sgAirCalculator;
	
	private Map<String, Calculator> serviceMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		
		this.serviceMap.put(Country.HONG_KONG.getCode(), hkAirCalculator);
		this.serviceMap.put(Country.SINGAPORE.getCode(), sgAirCalculator);
		this.serviceMap.put(Country.AUSTRALIA.getCode(), sgAirCalculator);
		this.serviceMap.put(Country.NEW_ZEALAND.getCode(), sgAirCalculator);
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
