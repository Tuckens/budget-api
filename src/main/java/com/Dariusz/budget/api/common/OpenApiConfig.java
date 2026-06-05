package com.Dariusz.budget.api.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI budgetApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Budget API")
                        .description("API do zarządzania budżetem osobistym")
                        .version("1.0"));
    }
}