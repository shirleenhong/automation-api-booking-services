package com.cwt.bpg.cbt.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.security.config.property")
public class PropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.security.config.property.source")
    @PropertySource(value = { "classpath:properties/security-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfigSource
    {

    }

}
