package com.api.slcontract.controllers;

import com.api.slcontract.domain.AuthenticationRequest;
import com.api.slcontract.domain.User;
import com.api.slcontract.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signIn")
	public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
		return this.authService.signIn(data);
	}

	@PostMapping("/signUp")
	public ResponseEntity signup(@RequestBody User user) {
		return this.authService.signUp(user);
	}
}