package com.cwt.bpg.cbt.tpromigration.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.cwt.bpg.cbt.tpromigration.config.condition.HkCondition;
import com.cwt.bpg.cbt.tpromigration.config.condition.IndiaCondition;
import com.cwt.bpg.cbt.tpromigration.config.condition.SgCondition;

@Configuration("com.cwt.bpg.cbt.tpromigration.tprodb.config.property")
public class TproPropertyConfig {

	@Conditional(IndiaCondition.class)
	@Configuration("com.cwt.bpg.cbt.tpromigration.tprodb.config.property.source")
	@PropertySource(value = { "classpath:properties/tprodb-in.properties" }, ignoreResourceNotFound = true)
	static class IndiaPropertyConfig {
	}

	@Conditional(HkCondition.class)
	@Configuration("com.cwt.bpg.cbt.tpromigration.tprodb.config.property.source")
	@PropertySource(value = { "classpath:properties/tprodb-hk.properties" }, ignoreResourceNotFound = true)
	static class HkPropertyConfig {
	}

	@Conditional(SgCondition.class)
	@Configuration("com.cwt.bpg.cbt.tpromigration.tprodb.config.property.source")
	@PropertySource(value = { "classpath:properties/tprodb-sg.properties" }, ignoreResourceNotFound = true)
	static class SgPropertyConfig {
	}
}
