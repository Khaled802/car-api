package com.example.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String info = jwtService.getInfo(request);
		
		if (info != null) {
			
			String user = info.split(",")[0];
			String role = info.split(",")[1];
			List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
//		log.info("user in filter: {}", user);
		filterChain.doFilter(request, response);
		
	}

}
