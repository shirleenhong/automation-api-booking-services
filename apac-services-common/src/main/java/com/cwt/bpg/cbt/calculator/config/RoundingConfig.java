package com.cwt.bpg.cbt.calculator.config;

import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class RoundingConfig {
	
	@Autowired 
	Environment env; 
	
	public RoundingMode getRoundingMode(String field, String countryCode) {
		Integer roundingProp = countryCode == null? null : env.getProperty("com.cwt.bpg.cbt.calc.rounding.".concat(field).concat(".").concat(countryCode.toUpperCase()), Integer.class);
		if (roundingProp == null) {
			roundingProp = env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.".concat(field).concat(".Default"), Integer.class);
		}
		return RoundingMode.valueOf(roundingProp);
	}

}
