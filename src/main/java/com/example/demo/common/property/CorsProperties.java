package com.example.demo.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
	private String[] allowedOrigins;
}