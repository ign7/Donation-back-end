package com.backendduation.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	SecurityFilter securityFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .cors() // Habilita o CORS
	        .and()
	        .csrf().disable() // Desabilita o CSRF
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
	            .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()
	            .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
	            .requestMatchers(HttpMethod.GET, "/solicitacoes/listartodos").permitAll()
	            .requestMatchers(HttpMethod.POST, "/solicitacoes/realizarsolicitacao/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/solicitacoes/solicitacaoporid/**").hasRole("DOADOR")
	            .requestMatchers(HttpMethod.PATCH, "/solicitacoes/acepptSolicitation/**").hasRole("DOADOR") 
	            .requestMatchers(HttpMethod.PATCH, "/solicitacoes/rejeitarSolicitation/**").hasRole("DOADOR") 
	            .requestMatchers(HttpMethod.GET, "/donations/todos").permitAll()
	            .requestMatchers(HttpMethod.GET, "/donations/pesquisardoacao/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/donations/cadastrardonation/**").hasRole("DOADOR")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	        .build();
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
