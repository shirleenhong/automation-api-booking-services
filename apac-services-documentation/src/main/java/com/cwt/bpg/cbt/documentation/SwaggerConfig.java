package com.cwt.bpg.cbt.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket swaggerForDev()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("dev")
                .apiInfo(apiInfo())
                .tags(appInfo(), exchangeOrder(), merchantFee(), serviceFees())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    @Bean
    public Docket swaggerForConsumer()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(appInfo(), exchangeOrder(), serviceFees())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.withMethodAnnotation(Internal.class)))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    @Bean
    public UiConfiguration uiConfig()
    {
        return new UiConfiguration(false, false, 0, 1, ModelRendering.EXAMPLE, false, DocExpansion.LIST, true, 10, OperationsSorter.ALPHA, false, TagsSorter.ALPHA, "");
    }

    private Tag merchantFee()
    {
        return new Tag("Merchant Fee", "Services related to Merchant Fee");
    }

    private Tag serviceFees()
    {
        return new Tag("Service Fees", "Services related to Service Fees");
    }

    private Tag exchangeOrder()
    {
        return new Tag("Exchange Order", "Services related to Exchange Order");
    }

    private Tag appInfo()
    {
        return new Tag("App Info", "Services that display application info");
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("APAC Services API")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
