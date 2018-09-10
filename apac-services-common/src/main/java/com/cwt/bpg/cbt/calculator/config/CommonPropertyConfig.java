package com.cwt.bpg.cbt.calculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.calculator.property.config")
public class CommonPropertyConfig {

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("local")
	@PropertySource(value = { "classpath:properties/common-local.properties" }, ignoreResourceNotFound = true)
	static class LocalPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("dev")
	@PropertySource(value = { "classpath:properties/common-dev.properties" }, ignoreResourceNotFound = true)
	static class DevPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("int")
	@PropertySource(value = { "classpath:properties/common-int.properties" }, ignoreResourceNotFound = true)
	static class IntPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("preprod")
	@PropertySource(value = { "classpath:properties/common-preprod.properties" }, ignoreResourceNotFound = true)
	static class PreprodPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("prod")
	@PropertySource(value = { "classpath:properties/common-prod.properties" }, ignoreResourceNotFound = true)
	static class ProdPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("perf")
	@PropertySource(value = { "classpath:properties/common-perf.properties" }, ignoreResourceNotFound = true)
	static class PerfPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.calculator.config.source")
	@Profile("sec")
	@PropertySource(value = { "classpath:properties/common-sec.properties" }, ignoreResourceNotFound = true)
	static class SecurityPropertyConfig {
	}

}
