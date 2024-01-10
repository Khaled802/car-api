package com.example.demo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.SignInDto;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> sigin(@RequestBody SignInDto signInDto) {
		var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
		if (!auth.isAuthenticated()) {
			return ResponseEntity.badRequest().build();
		}
		String token = jwtService.generateToken(auth.getName(), auth.getAuthorities().toArray()[0].toString());
		return ResponseEntity.ok(new MessageDto(token));
	}
}
