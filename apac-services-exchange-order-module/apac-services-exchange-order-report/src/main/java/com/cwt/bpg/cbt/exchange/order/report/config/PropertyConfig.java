package com.cwt.bpg.cbt.exchange.order.report.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property")
public class PropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @PropertySource(value = { "classpath:properties/eo-report-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfigSource
    {
        
    }
    
}
