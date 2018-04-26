package com.cwt.bpg.cbt.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cwt.bpg.cbt.calculator.CommonCalculator;

@Configuration("com.cwt.bpg.cbt.calculator.config")
@Import({ CommonPropertyConfig.class })
public class CommonConfig {

	@Bean(name="commonCalculator")
	public CommonCalculator commonCalculator() {
		return new CommonCalculator();
	}

	@Bean(name="scaleConfig")
	public ScaleConfig scaleConfig() {
		return new ScaleConfig();
	}
}
