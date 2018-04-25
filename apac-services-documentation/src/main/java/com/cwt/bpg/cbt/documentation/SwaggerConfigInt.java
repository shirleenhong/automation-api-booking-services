package com.cwt.bpg.cbt.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("int")
public class SwaggerConfigInt extends SwaggerConfigBase
{
    @Bean
    public Docket swaggerForConsumer()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.withMethodAnnotation(Internal.class)))
                .paths(Predicates.not(PathSelectors.regex(
                        "/(error|auditevents|autoconfig|configprops|dump|info|mappings|springbeans|trace|env|health|heapdump|loggers|metrics).*")))
                .build()
                .groupName("apac-services")
                .apiInfo(apiInfo())
                .tags(appInfo(), exchangeOrder(), serviceFees())
                .securitySchemes(Lists.newArrayList(securityScheme()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }
}
