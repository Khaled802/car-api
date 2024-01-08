package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Bean
	OpenAPI openApi() {
		return new OpenAPI().info(new Info().title("Car Rest Api").version("1.0.0"));
	}
}
