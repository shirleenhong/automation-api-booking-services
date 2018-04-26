package com.cwt.bpg.cbt.calculator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ScaleConfig {
	
	@Autowired 
	Environment env; 
	
	public int getScale(String countryCode) {
		Integer scaleProp = env.getProperty("com.cwt.bpg.cbt.calc.scale.".concat(countryCode), Integer.class);
		return scaleProp != null 
				? scaleProp.intValue() 
				: env.getRequiredProperty("com.cwt.bpg.cbt.calc.scale.Default", 
						Integer.class).intValue();		
	}

}
