package com.cwt.bpg.cbt.service.fee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.service.fee.calculator.ServiceFeeCalculator;

@Configuration("com.cwt.bpg.cbt.service.fee.config")
public class ServiceFeeConfig {
	
	@Bean
    public ServiceFeeCalculator serviceFeeCalculator()
    {
        return new ServiceFeeCalculator();
    }
}
