package com.cwt.bpg.cbt.calculator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.math.RoundingMode;

public class RoundingConfig {
	
	@Autowired 
	Environment env; 
	
	public RoundingMode getRoundingMode(String countryCode) {
		Integer roundingProp = countryCode == null? null : env.getProperty("com.cwt.bpg.cbt.calc.rounding.".concat(countryCode.toUpperCase()), Integer.class);
		if (roundingProp == null)
			roundingProp = env.getRequiredProperty("com.cwt.bpg.cbt.calc.rounding.Default", Integer.class);

		return RoundingMode.valueOf(roundingProp);
	}

}
