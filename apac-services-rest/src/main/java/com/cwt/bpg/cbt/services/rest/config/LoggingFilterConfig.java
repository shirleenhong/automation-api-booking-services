package com.cwt.bpg.cbt.services.rest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.services.rest.filter.LoggingFilter;

@Configuration
public class LoggingFilterConfig {

	@Bean
	public FilterRegistrationBean filter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new LoggingFilter());
	    registration.addUrlPatterns("/*");
	    return registration;
	}
}