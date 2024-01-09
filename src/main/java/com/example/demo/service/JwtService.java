package com.example.demo.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.codec.Decoder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {
	static final long EXPRIATION_TIME = 86500000;
	static final String PREFIX = "Bearer";
	@Value("${jwt.token.secret}")
	private String secret;

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(EXPRIATION_TIME + System.currentTimeMillis()))
				.signWith(getSigningKey()).compact();
	}
	
	
	public String getUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token == null) {
			return null;
		}
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build() 
				.parseClaimsJws(token.replace(PREFIX+" ", "")).getBody().getSubject();
		
	}
}
