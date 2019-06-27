package com.cwt.bpg.cbt.services.rest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.cwt.bpg.cbt.services.rest.filter.LoggingFilter;

@Configuration
public class LoggingFilterConfig
{

    @Bean
    public FilterRegistrationBean<LoggingFilter> filter()
    {
        final FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>(new LoggingFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        
        return registration;
    }
}
