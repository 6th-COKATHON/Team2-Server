package com.example.demo.common.swagger.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.common.property.SwaggerProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

	private final SwaggerProperties swaggerProperties;

	@Bean
	public OpenAPI openAPI() {

		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI()
			.servers(List.of(new Server().url(swaggerProperties.getUrl()).description("Backend Server")))
			.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
			.security(Collections.singletonList(securityRequirement))
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("API DESCRIPTION")
			.description("API documentation for the application.")
			.version("1.0.0");
	}
}
