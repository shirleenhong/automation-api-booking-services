package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.exchange.order.config.property")
public class PropertyConfig
{
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("local")
    @PropertySource(value = { "classpath:properties/exchange-order-local.properties" }, ignoreResourceNotFound = true)
    static class LocalPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/exchange-order-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/exchange-order-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("sec")
    @PropertySource(value = { "classpath:properties/exchange-order-sec.properties" }, ignoreResourceNotFound = true)
    static class SecurityPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/exchange-order-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/exchange-order-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.config.property.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/exchange-order-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

}
