package com.cwt.bpg.cbt.encryptor.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.encryptor.impl.property.config")
public class PropertyConfig
{
    @Configuration("com.cwt.encryptor.impl.property.config.source")
    @PropertySource(value = { "classpath:properties/encryptor-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfigSource
    {

    }

}
