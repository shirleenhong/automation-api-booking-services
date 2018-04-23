package com.cwt.bpg.cbt.services.rest.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.google.common.base.Predicates;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
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
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("((?!\\/error).)*"))
                .build();
    }

    @Bean
    public Docket swaggerForConsumer()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.withMethodAnnotation(Internal.class)))
                .paths(PathSelectors.regex("((?!\\/error).)*"))
                .build();
    }
}
