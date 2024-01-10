package com.example.demo.configuration;

import java.util.Arrays;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.service.UserDatailsServiceImp;
import com.example.demo.util.AuthEntryPoint;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final UserDatailsServiceImp userDetailsService;
	private final AuthenticationFilter authenticationFilter;
	private final AuthEntryPoint authExceptionHandler;

	
	void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	} 

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager auhtAuthenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrt -> csrt.disable())
				.cors(withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authExceptionHandler))

				.authorizeHttpRequests(requests -> requests
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers("/admin/**")
						.hasRole("ADMIN")
						.anyRequest().authenticated()
				);
//				.anyRequest().permitAll()
		return http.build();
		
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    UrlBasedCorsConfigurationSource source =
	       new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(Arrays.asList("*"));
	    config.setAllowedMethods(Arrays.asList("*"));
	    config.setAllowedHeaders(Arrays.asList("*"));
	    config.setAllowCredentials(false);
	    config.applyPermitDefaultValues();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
}
