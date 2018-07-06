package com.cwt.bpg.cbt.exchange.order.report.config;

import org.springframework.context.annotation.Configuration;


@Configuration("com.cwt.bpg.cbt.exchange.order.report.config")
public class ExchangeOrderReportConfig {

	/*@Lazy
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
	}*/
}
