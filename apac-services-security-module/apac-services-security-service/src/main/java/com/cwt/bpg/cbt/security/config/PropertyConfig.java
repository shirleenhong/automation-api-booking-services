package com.cwt.bpg.cbt.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.security.config.property")
public class PropertyConfig {
	private PropertyConfig() {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("local")
	@PropertySource(value = { "classpath:properties/security-local.properties" }, ignoreResourceNotFound = true)
	static class LocalPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("dev")
	@PropertySource(value = { "classpath:properties/security-dev.properties" }, ignoreResourceNotFound = true)
	static class DevPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("int")
	@PropertySource(value = { "classpath:properties/security-int.properties" }, ignoreResourceNotFound = true)
	static class IntPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("sec")
	@PropertySource(value = { "classpath:properties/security-sec.properties" }, ignoreResourceNotFound = true)
	static class SecurityPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("perf")
	@PropertySource(value = { "classpath:properties/security-perf.properties" }, ignoreResourceNotFound = true)
	static class PerfPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("preprod")
	@PropertySource(value = { "classpath:properties/security-preprod.properties" }, ignoreResourceNotFound = true)
	static class PreprodPropertyConfig {
	}

	@Configuration("com.cwt.bpg.cbt.security.config.property.source")
	@Profile("prod")
	@PropertySource(value = { "classpath:properties/security-prod.properties" }, ignoreResourceNotFound = true)
	static class ProdPropertyConfig {
	}

}
