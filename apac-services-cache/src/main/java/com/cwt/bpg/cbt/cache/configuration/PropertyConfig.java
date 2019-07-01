package com.cwt.bpg.cbt.cache.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.cache.config.property")
public class PropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.cache.config.property.source")
    @PropertySource(value = { "classpath:properties/cache-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {

    }

}
