package com.cwt.bpg.cbt.exchange.order.report.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.*;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;

import freemarker.template.TemplateException;


@Configuration("com.cwt.bpg.cbt.exchange.order.report.config")
public class ExchangeOrderReportConfig {

	@Lazy
	@Bean(name = "freemarkerConfig")
	public freemarker.template.Configuration freemarkerConfig() throws ExchangeOrderException {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setDefaultEncoding(StandardCharsets.UTF_8.name());
		bean.setTemplateLoaderPath("classpath:templates");

		try {
			return bean.createConfiguration();
		}
		catch (IOException | TemplateException e) {
			throw new ExchangeOrderException("Template error", e);
		}
	}
}
