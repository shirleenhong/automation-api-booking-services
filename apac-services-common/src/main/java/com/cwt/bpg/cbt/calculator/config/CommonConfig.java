package com.cwt.bpg.cbt.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.calculator.CommonCalculator;

@Configuration("com.cwt.bpg.cbt.calculator.config")
public class CommonConfig {

	@Bean
    public CommonCalculator commonCalculator()
    {
        return new CommonCalculator();
    }
}
