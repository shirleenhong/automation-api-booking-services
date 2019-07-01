package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.exchange.order.config.property")
public class PropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @PropertySource(value = { "classpath:properties/exchange-order-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfigSource
    {

    }

}
