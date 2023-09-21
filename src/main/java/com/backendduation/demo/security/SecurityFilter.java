package com.backendduation.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenService service;
	
	@Autowired
	UserRepository  repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var token= this.recoverToken(request);
		if(token!=null) {
			var login=service.validacaoToken(token);
			UserDetails user= repository.findBylogin(login);
			
			var auteticacao = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auteticacao);
		}
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader=request.getHeader("Authorization");
		if(authHeader==null) return null;					
		return	authHeader.replace("Bearer ", "");		
	}
	
	

}
