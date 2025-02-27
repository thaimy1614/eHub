package com.datn.resource_service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "A JWT token is required to access this API. JWT token can be obtain by /identity/login " +
                "or the /identity/oauth2/authorization/{oAuthProvider} API"
)
public class OpenAPIConfiguration {
    @Value("${springdoc.servers}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("User Service API").version("v3"))
                .addServersItem(new Server().url(serverUrl).description("API Gateway"));
    }
}
