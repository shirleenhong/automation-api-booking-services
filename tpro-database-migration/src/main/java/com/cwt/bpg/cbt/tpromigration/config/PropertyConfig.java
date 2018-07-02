package com.cwt.bpg.cbt.tpromigration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property")
public class PropertyConfig
{
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("local")
    @PropertySource(value = { "classpath:properties/app-local.properties" }, ignoreResourceNotFound = true)
    static class LocalPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("dev")
    @PropertySource(value = { "classpath:properties/app-dev.properties" }, ignoreResourceNotFound = true)
    static class DevPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("int")
    @PropertySource(value = { "classpath:properties/app-int.properties" }, ignoreResourceNotFound = true)
    static class IntPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("perf")
    @PropertySource(value = { "classpath:properties/app-perf.properties" }, ignoreResourceNotFound = true)
    static class PerfPropertyConfig
    {
    }

    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("preprod")
    @PropertySource(value = { "classpath:properties/app-preprod.properties" }, ignoreResourceNotFound = true)
    static class PreprodPropertyConfig
    {
    }
    
    @Configuration("com.cwt.bpg.cbt.tpromigration.mssqldb.config.property.source")
    @Profile("prod")
    @PropertySource(value = { "classpath:properties/app-prod.properties" }, ignoreResourceNotFound = true)
    static class ProdPropertyConfig
    {
    }

}
