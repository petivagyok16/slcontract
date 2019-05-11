package com.api.slcontract.controllers;

import com.api.slcontract.domain.AuthenticationRequest;
import com.api.slcontract.domain.User;
import com.api.slcontract.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
		log.info("auth data: " + data);
		return this.authService.signIn(data);
	}

	@PostMapping("/signup")
	public ResponseEntity signup(@Valid @RequestBody User user) {
		log.info("user: " + user);
		return this.authService.signUp(user);
	}
}