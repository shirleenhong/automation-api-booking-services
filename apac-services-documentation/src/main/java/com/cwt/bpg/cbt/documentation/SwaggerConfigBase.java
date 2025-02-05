package com.cwt.bpg.cbt.documentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.*;

public class SwaggerConfigBase {
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

    Tag agent() {
        return new Tag("Agent", "Maintenance of Agents.");
    }

    Tag airContract() {
        return new Tag("Air Contract", "Services related to Air Contract.\nImportant field: **fopCode (BCODE)**.");
    }

    Tag airTransaction() {
        return new Tag("Air Transaction", "Services related to Air Transaction.\nImportant field: **passthroughType**.");
    }

    Tag airline() {
        return new Tag("Airline", "Maintenance of Airlines.");
    }

    Tag airlineRules() {
        return new Tag("Airline Rules", "Maintenance of Airline Rules.\nImportant field: **includeYqCommission**");
    }

    Tag airport() {
        return new Tag("Airport", "Maintenance of Airports.");
    }

    Tag appInfo() {
        return new Tag("App Info", "Health-check endpoint.");
    }

    Tag clientGstInfo() {
        return new Tag("Client GST Info", "Maintenance of Client GST Information");
    }

    Tag client() {
        return new Tag("Clients", "Services related to Clients.");
    }

    Tag codif() {
        return new Tag("Codif", "Services related to Codif");
    }

    Tag exchangeOrder() {
        return new Tag("Exchange Order", "Services related to Exchange Order.");
    }

    Tag carVendors() {
        return new Tag("Exchange Order - Car Vendor", "Maintenance of EO car vendors.");
    }

    Tag hotelRoomTypes() {
        return new Tag("Exchange Order - Hotel Room Types", "Maintenance of EO hotel room types.");
    }

    Tag vmpdReasonCodes() {
        return new Tag("Exchange Order - VMPD Reason Codes", "Maintenance of EO VMPD reasons for issue.");
    }

    Tag airMiscInfo() {
        return new Tag("Exchange Order - Air Misc Info", "Maintenance of Air Misc Infos.");
    }

    Tag insurance() {
        return new Tag("Insurance", "Maintenance of Insurance Plans.");
    }

    Tag merchantFee() {
        return new Tag("Merchant Fee", "Maintenance of Merchant Fee.");
    }

    Tag obtList() {
        return new Tag("OBT List", "Maintenance of OBT List.");
    }

    Tag otherServiceFees() {
        return new Tag("Other Service Fees", "Services related to computation of EO service fees.");
    }

    Tag products() {
        return new Tag("Products", "Services related to Products.");
    }

    Tag remarks() {
        return new Tag("Remarks", "Services related to Remarks.");
    }

    Tag serviceFees() {
        return new Tag("Service Fees", "Services related to Service Fees");
    }

    Tag user() {
        return new Tag("User", "Maintenance of Users");
    }
    
    Tag reportHeaders() {
        return new Tag("Report Header", "Maintenance of Report Headers.");
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("APAC Services API")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version(applicationVersion).build();
    }
}
