package com.backendduation.demo.Cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:3000", "http://localhost:8080")
				.allowedMethods("GET", "POST", "PUT","PATCH","DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}

}
