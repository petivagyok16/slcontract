package com.api.slcontract.controllers;

import com.api.slcontract.domain.AuthenticationRequest;
import com.api.slcontract.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
		return this.authService.signin(data);
	}
}