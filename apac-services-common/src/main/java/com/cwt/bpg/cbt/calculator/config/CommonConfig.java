package com.cwt.bpg.cbt.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cwt.bpg.cbt.utils.ServiceUtils;

@Configuration("com.cwt.bpg.cbt.calculator.config")
@Import({ CommonPropertyConfig.class })
public class CommonConfig {

	@Bean(name="scaleConfig")
	public ScaleConfig scaleConfig() {
		return new ScaleConfig();
	}
	
	@Bean(name="serviceUtils")
	public ServiceUtils serviceUtils() {
		return new ServiceUtils();
	}

}
