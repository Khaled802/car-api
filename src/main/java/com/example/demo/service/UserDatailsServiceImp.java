package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.AppUser;
import com.example.demo.domain.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDatailsServiceImp implements UserDetailsService {
	private final AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> appUser = appUserRepository.findByUsername(username);
		if (appUser.isEmpty())
			throw new UsernameNotFoundException("User not found");
		AppUser curUser = appUser.get();
		return User.builder().username(curUser.getUsername()).password(curUser.getPassword()).roles(curUser.getRole())
				.build();
	}

}
