package com.api.slcontract.services;

import com.api.slcontract.domain.AuthenticationRequest;
import com.api.slcontract.domain.User;
import com.api.slcontract.exception.CustomException;
import com.api.slcontract.repository.UserRepository;
import com.api.slcontract.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity signIn(AuthenticationRequest data) {
		try {
			String username = data.getUsername();
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
			String token = this.jwtTokenProvider.createToken(username, this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found")).getRoles());

			Map<String, Object> model = new HashMap<>();
			model.put("user", username);
			model.put("token", token);
			return ResponseEntity.ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}

	public ResponseEntity signUp(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = this.userRepository.save(user);
			String token = this.jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRoles());
			Map<String, Object> model = new HashMap<>();
			model.put("user", savedUser.getUsername());
			model.put("token", token);
			return ResponseEntity.ok(model);
		} else {
			throw new CustomException("Username is already in use", HttpStatus.CONFLICT);
		}
	}

}
