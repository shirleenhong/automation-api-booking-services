package com.cwt.bpg.cbt.calculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.calculator.property.config")
public class CommonPropertyConfig
{

    @Configuration("com.cwt.bpg.cbt.calculator.config.source")
    @PropertySource(value = { "classpath:properties/common-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
    static class PropertyConfig
    {

    }

}
