package com.api.slcontract.security;

import com.api.slcontract.domain.User;
import com.api.slcontract.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository users;

	public CustomUserDetailsService(UserRepository users) {
		this.users = users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.users.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		final Optional<User> user = users.findByUsername(username);
//
//		if (user.isEmpty()) {
//			throw new UsernameNotFoundException("User '" + username + "' not found");
//		}
//
//		return org.springframework.security.core.userdetails.User//
//						.withUsername(username)//
//						.password(user.get().getPassword())//
//						.authorities(user.get().getRoles())//
//						.accountExpired(false)//
//						.accountLocked(false)//
//						.credentialsExpired(false)//
//						.disabled(false)//
//						.build();
//	}
}
