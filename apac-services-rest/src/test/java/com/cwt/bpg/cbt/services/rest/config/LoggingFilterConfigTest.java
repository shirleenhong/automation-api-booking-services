package com.cwt.bpg.cbt.services.rest.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

public class LoggingFilterConfigTest {

	private LoggingFilterConfig config = new LoggingFilterConfig();
	
	@Test
	public void canReturnRegistrationBean() {
		FilterRegistrationBean filter = config.filter();
		assertNotNull(filter);
	}
}
