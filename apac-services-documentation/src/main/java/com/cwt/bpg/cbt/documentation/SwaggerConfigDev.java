package com.cwt.bpg.cbt.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfigDev extends SwaggerConfigBase {
    @SuppressWarnings("unchecked")
    @Bean
    public Docket swaggerForDev() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.and(
                        Predicates.not(PathSelectors.regex("/error.*"))))
                .build()
                .groupName("apac-services")
                .apiInfo(apiInfo())
				.tags(appInfo(), airTransaction(), airline(), airlineRules(), airport(),
						airContract(), client(), exchangeOrder(), merchantFee(),
						serviceFees(), otherServiceFees(), products(), insurance(),
						obtList())
                .securitySchemes(Lists.newArrayList(securityScheme()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }
}
