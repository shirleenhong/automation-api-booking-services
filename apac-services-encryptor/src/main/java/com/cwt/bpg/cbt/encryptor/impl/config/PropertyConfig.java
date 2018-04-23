package com.cwt.bpg.cbt.encryptor.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.encryptor.impl.property.config")
public class PropertyConfig
{
    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("local")
    @PropertySource(value = { "classpath:properties/encryptor-local.properties" }, ignoreResourceNotFound = true)
    static class LocalPropertyConfig
    {
    }
    
    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/encryptor-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/encryptor-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }

    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/encryptor-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }

    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/encryptor-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/encryptor-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @Profile("sec")
    @PropertySource(value = { "classpath:properties/encryptor-sec.properties" }, ignoreResourceNotFound = true)
    static class SecurityPropertyConfig
    {
    }

}
