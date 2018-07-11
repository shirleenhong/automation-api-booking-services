package com.cwt.bpg.cbt.exchange.order.report.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import org.springframework.context.annotation.*;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.TemplateException;


@Configuration("com.cwt.bpg.cbt.exchange.order.report.config")
public class ExchangeOrderReportConfig {

	@Lazy
	@Bean(name = "freemarkerConfig")
	public freemarker.template.Configuration freemarkerConfig() throws ApiServiceException {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setDefaultEncoding(StandardCharsets.UTF_8.name());
		bean.setTemplateLoaderPath("classpath:templates");

		try {
			return bean.createConfiguration();
		}
		catch (IOException | TemplateException e) {
			throw new ApiServiceException("Template error", e);
		}
	}
}
