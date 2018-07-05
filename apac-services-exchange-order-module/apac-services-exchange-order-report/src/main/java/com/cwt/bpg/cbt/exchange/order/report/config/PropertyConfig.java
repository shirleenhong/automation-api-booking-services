package com.cwt.bpg.cbt.exchange.order.report.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property")
public class PropertyConfig
{
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("local")
    @PropertySource(value = { "classpath:properties/eo-report-local.properties" }, ignoreResourceNotFound = true)
    static class LocalPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/eo-report-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/eo-report-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("sec")
    @PropertySource(value = { "classpath:properties/eo-report-sec.properties" }, ignoreResourceNotFound = true)
    static class SecurityPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/eo-report-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/eo-report-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.exchange.order.report.config.property.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/eo-report-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

}
