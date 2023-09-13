package com.backendduation.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
		return http.
				csrf(csrf->csrf.disable())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize->authorize
						.requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll()
						.requestMatchers(HttpMethod.POST,"/usuarios/register").permitAll()
						.requestMatchers(HttpMethod.POST,"/usuarios").hasRole("ADMIN").anyRequest()
						.authenticated()).build();
		
	}
	
	
	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration confugration) throws Exception {
		return confugration.getAuthenticationManager();
	}
	
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

}
