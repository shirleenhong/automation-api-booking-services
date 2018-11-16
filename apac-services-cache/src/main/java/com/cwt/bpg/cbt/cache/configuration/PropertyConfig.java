package com.cwt.bpg.cbt.cache.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.cache.config.property")
public class PropertyConfig
{
    
    
    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/cache-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/cache-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("sec")
    @PropertySource(value = { "classpath:properties/cache-sec.properties" }, ignoreResourceNotFound = true)
    static class SecurityPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/cache-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/cache-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/cache-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

}
