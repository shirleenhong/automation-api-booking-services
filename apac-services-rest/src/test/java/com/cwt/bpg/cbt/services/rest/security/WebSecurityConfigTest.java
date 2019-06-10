package com.cwt.bpg.cbt.services.rest.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.services.rest.security.WebSecurityConfig.ApiWebSecurityConfigurationAdapter;
import com.cwt.bpg.cbt.services.rest.security.WebSecurityConfig.TokenSecurityConfigurerAdapter;


public class WebSecurityConfigTest {

    private ApiWebSecurityConfigurationAdapter wsc = new ApiWebSecurityConfigurationAdapter();

    @SuppressWarnings("unchecked")
    @Test
    public void canConfigureWebSecurity() throws Exception {
        ObjectPostProcessor<Object> objectPostProcessor = mock(ObjectPostProcessor.class);
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        WebSecurity ws = new WebSecurity(objectPostProcessor);
        ws.setApplicationContext(applicationContext);

        wsc.configure(ws);

        List<RequestMatcher> field = (List<RequestMatcher>) ReflectionTestUtils.getField(ws, "ignoredRequests");
        assertTrue(field.size() > 0);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canConfigureWebHttpSecurity() throws Exception {
        ObjectPostProcessor<Object> objectPostProcessor = mock(ObjectPostProcessor.class);
        Map<Class<? extends Object>, Object> sharedObjects = mock(Map.class);
        AuthenticationManagerBuilder authenticationBuilder = mock(AuthenticationManagerBuilder.class);

        HttpSecurity http = new HttpSecurity(objectPostProcessor, authenticationBuilder, sharedObjects);

        wsc.configure(http);

        List<Filter> field = (List<Filter>) ReflectionTestUtils.getField(http, "filters");
        assertNotNull(field);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canConfigureHttpSecurity() throws Exception {
        ObjectPostProcessor<Object> objectPostProcessor = mock(ObjectPostProcessor.class);
        Map<Class<? extends Object>, Object> sharedObjects = mock(Map.class);
        AuthenticationManagerBuilder authenticationBuilder = mock(AuthenticationManagerBuilder.class);

        HttpSecurity http = new HttpSecurity(objectPostProcessor, authenticationBuilder, sharedObjects);

        TokenSecurityConfigurerAdapter tsca = new TokenSecurityConfigurerAdapter();
        tsca.configure(http);

        List<Filter> field = (List<Filter>) ReflectionTestUtils.getField(http, "filters");
        assertTrue(field.size() > 0);
    }
}
