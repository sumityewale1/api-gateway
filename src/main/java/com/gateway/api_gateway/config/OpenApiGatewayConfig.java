package com.gateway.api_gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class OpenApiGatewayConfig {

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/user/**")   // Gateway route path
                .build();
    }

    @Bean
    public GroupedOpenApi orderServiceApi() {
        return GroupedOpenApi.builder()
                .group("order-service")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentServiceApi() {
        return GroupedOpenApi.builder()
                .group("payment-service")
                .pathsToMatch("/payment/**")
                .build();
    }

}
