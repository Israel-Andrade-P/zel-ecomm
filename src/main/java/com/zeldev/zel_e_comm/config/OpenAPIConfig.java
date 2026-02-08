package com.zeldev.zel_e_comm.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer Auth");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info()
                        .title("Zel's E-Commerce APIs")
                                .version("1.0.0")
                                .description("APIs written in Spring framework, for general e-comm stuff")
                                .license(new License().name("Apache 2.0").url("https://dummy_url.com"))
                                .contact(new Contact().name("Israel Andrade").email("zeldev@gmail.com").url("https://github.com/Israel-Andrade-P"))
                        )
                .externalDocs(new ExternalDocumentation().description("Project Documentation").url("https://zeldev.com"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
