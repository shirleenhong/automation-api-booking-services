package com.cwt.bpg.cbt.documentation;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigBase
{
    @Bean
    UiConfiguration uiConfig()
    {
        return UiConfigurationBuilder.builder()
                .deepLinking(false)
                .displayOperationId(false)
                .defaultModelsExpandDepth(0)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.LIST)
                .filter(true)
                .maxDisplayedTags(10)
                .operationsSorter(OperationsSorter.ALPHA)
                .tagsSorter(TagsSorter.ALPHA)
                .build();
    }

    SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(Predicates.not(PathSelectors.regex("/app-info")))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("Power Express Token", authorizationScopes));
    }

    ApiKey securityScheme()
    {
        return new ApiKey("Power Express Token", "Authorization", "header");
    }

    Tag merchantFee()
    {
        return new Tag("Merchant Fee", "Services related to Merchant Fee");
    }

    Tag serviceFees()
    {
        return new Tag("Service Fees", "Services related to Service Fees");
    }

    Tag exchangeOrder()
    {
        return new Tag("Exchange Order", "Services related to Exchange Order");
    }

    Tag appInfo()
    {
        return new Tag("App Info", "Services that display application info");
    }

    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("APAC Services API")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
