package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignInDto {
	private String username;
	private String password;
}
