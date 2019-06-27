package com.cwt.bpg.cbt.services.rest.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.cwt.bpg.cbt.services.rest.filter.LoggingFilter;

public class LoggingFilterConfigTest
{

    private LoggingFilterConfig config = new LoggingFilterConfig();

    @Test
    public void canReturnRegistrationBean()
    {
        final FilterRegistrationBean<LoggingFilter> filter = config.filter();
        assertNotNull(filter);
    }
}
