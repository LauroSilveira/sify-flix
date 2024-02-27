package com.lauro.sifyflixapi.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .security(List.of())
                .info(new Info().title("sify-flix-api API")
                        .description("This is an API Rest that connect with H2 memory, this API implements the basic HTTP methods, such GET, POST, PUT and DELETE. " +
                                " You can Create, Update, Get All, Get by Id, Get by Spaceship Name and Delete")
                        .contact(new Contact()
                                .name("Lauro Correia")
                                .email("lauro.silveira@outlook.com.br"))
                        .license(new License().name("MIT"))
                );
    }
}
