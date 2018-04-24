package com.cwt.bpg.cbt.documentation;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(Predicates.not(PathSelectors.regex("/error.*")))
                    .build()
                .groupName("dev")
                .apiInfo(apiInfo())
                .tags(appInfo(), exchangeOrder(), merchantFee(), serviceFees())
                .securitySchemes(Lists.newArrayList(securityScheme()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }

    @Bean
    public Docket swaggerForConsumer()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(Predicates.not(RequestHandlerSelectors.withMethodAnnotation(Internal.class)))
                    .paths(Predicates.not(PathSelectors.regex(
                            "/(error|auditevents|autoconfig|configprops|dump|info|mappings|springbeans|trace|env|health|heapdump|loggers|metrics).*")))
                    .build()
                .apiInfo(apiInfo())
                .tags(appInfo(), exchangeOrder(), serviceFees())
                .securitySchemes(Lists.newArrayList(securityScheme()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }

    @Bean
    public UiConfiguration uiConfig()
    {
        new UiConfiguration(false, false, 0, 1, ModelRendering.EXAMPLE, false, DocExpansion.LIST, true, 10, OperationsSorter.ALPHA, false, TagsSorter.ALPHA, "");
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

    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(Predicates.not(PathSelectors.regex("/app-info")))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("Power Express Token", authorizationScopes));
    }

    private ApiKey securityScheme()
    {
        return new ApiKey("Power Express Token", "Authorization", "header");
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
