package com.cwt.bpg.cbt.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.mongodb.config.property")
public class PropertyConfig
{
    
    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("local")
    @PropertySource(value = { "classpath:properties/mongodb-local.properties" }, ignoreResourceNotFound = true)
    static class LocalPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/mongodb-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/mongodb-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("sec")
    @PropertySource(value = { "classpath:properties/mongodb-sec.properties" }, ignoreResourceNotFound = true)
    static class SecurityPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/mongodb-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/mongodb-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/mongodb-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

}
