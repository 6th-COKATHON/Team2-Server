package com.example.demo.common.property.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.demo.common.property.CorsProperties;
import com.example.demo.common.property.SwaggerProperties;

// Global properties configuration
@Configuration
@EnableConfigurationProperties(value = {
	CorsProperties.class,
	SwaggerProperties.class
})
public class PropertyConfig {
}