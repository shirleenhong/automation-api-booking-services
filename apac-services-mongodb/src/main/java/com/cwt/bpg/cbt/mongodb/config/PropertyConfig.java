package com.cwt.bpg.cbt.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.mongodb.config.property")
public class PropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.mongodb.config.property.source")
    @PropertySource(value = { "classpath:properties/mongodb-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfigSource
    {
        
    }

}
