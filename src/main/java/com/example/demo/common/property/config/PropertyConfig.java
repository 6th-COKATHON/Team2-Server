package com.example.demo.common.property.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.demo.common.property.AIProperties;
import com.example.demo.common.property.CorsProperties;
import com.example.demo.common.property.SecurityProperties;
import com.example.demo.common.property.SwaggerProperties;
import com.example.demo.common.property.TokenProperty;

// Global properties configuration
@Configuration
@EnableConfigurationProperties(value = {
	CorsProperties.class,
	SwaggerProperties.class,
	TokenProperty.class,
	SecurityProperties.class,
	AIProperties.class
})
public class PropertyConfig {
}