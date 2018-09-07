package com.cwt.bpg.cbt.services.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("com.cwt.bpg.cbt.services.rest.config.property")
public class PropertyConfig {

	private PropertyConfig() {
	}

	@Configuration("com.cwt.bpg.cbt.services.rest.config.property.source")
	@PropertySource(value = { "classpath:properties/build.properties" }, ignoreResourceNotFound = true)
	static class LocalPropertyConfig {
	}

}
