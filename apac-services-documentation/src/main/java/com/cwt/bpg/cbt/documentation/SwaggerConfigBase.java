package com.cwt.bpg.cbt.documentation;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.*;

import java.util.List;

public class SwaggerConfigBase
{
    @Value("${com.bpg.cbt.apac.service.version}")
    private String applicationVersion;

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(false)
                .displayOperationId(false)
                .defaultModelsExpandDepth(0)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.LIST)
                .filter(true)
                .maxDisplayedTags(30)
                .operationsSorter(OperationsSorter.ALPHA)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }

    SecurityContext securityContext() {
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

	ApiKey securityScheme() {
		return new ApiKey("Power Express Token", "Authorization", "header");
	}

	Tag merchantFee() {
		return new Tag("Merchant Fee", "Services related to Merchant Fee");
	}

    Tag serviceFees() {
        return new Tag("Service Fees", "Services related to Service Fees");
    }
    
    Tag otherServiceFees() {
        return new Tag("Other Service Fees", "Services related to Other Service Fees");
    }
    
    Tag products() {
        return new Tag("Products", "Services related to Products");
    }
    
    Tag insurance() {
        return new Tag("Insurance", "Services related to Insurance");
    }

    Tag exchangeOrder() {
        return new Tag("Exchange Order", "Services related to Exchange Order");
    }

	Tag appInfo() {
		return new Tag("App Info", "Services that display application info");
	}

	Tag obtList() {
		return new Tag("OBT List", "Services related to OBT List");
	}

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("APAC Services API")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
				.version(applicationVersion).build();
	}
}
