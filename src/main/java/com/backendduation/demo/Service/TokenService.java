package com.backendduation.demo.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backendduation.demo.Entity.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algoritmo =Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("application-userautethication")
					.withSubject(user.getUsername())
					.withExpiresAt(ExpiracaoToken())
					.sign(algoritmo);
			return token;
		}catch(JWTCreationException e){
			throw new RuntimeException("erro na geração do Token",e);
		}
	}
	
	
	public String validacaoToken(String token) {
		try {
			Algorithm algoritmo =Algorithm.HMAC256(secret);
			 return JWT.require(algoritmo)
						.withIssuer("application-userautethication")
						.build()
						.verify(token)
						.getSubject();
			 
		}catch(JWTVerificationException e) {
			return "";
		}
	}
	
	private Instant ExpiracaoToken() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
