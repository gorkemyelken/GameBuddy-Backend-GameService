package com.gamebuddy.GameService.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application-description}") String description,
            @Value("${application-version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("GameBuddy Game Service")
                        .version(version)
                        .description(description)
                        .license(new License().name("GameBuddy Game API Licence")));
    }
}
