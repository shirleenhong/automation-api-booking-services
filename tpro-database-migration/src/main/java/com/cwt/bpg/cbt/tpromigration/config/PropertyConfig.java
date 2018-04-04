package com.cwt.bpg.cbt.tpromigration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.tpromigration.config.property")
public class PropertyConfig
{
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.config.property.source")
    @Profile("hk")
    @PropertySource(value = { "classpath:properties/tprodb-hk.properties" }, ignoreResourceNotFound = true)
    static class HkPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.config.property.source")
    @Profile("sg")
    @PropertySource(value = { "classpath:properties/tprodb-sg.properties" }, ignoreResourceNotFound = true)
    static class SgPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.tpromigration.config.property.source")
    @Profile("in")
    @PropertySource(value = { "classpath:properties/tprodb-in.properties" }, ignoreResourceNotFound = true)
    static class InPropertyConfig
    {
    }

}
