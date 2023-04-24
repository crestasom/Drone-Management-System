package com.crestasom.dms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.request.AuthRequest;
import com.crestasom.dms.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthController {
	private AuthService authService;

	@PostMapping("/auth")
	public ResponseBean authenticate(@RequestBody AuthRequest authRequest) {
		return authService.checkLogin(authRequest);
	}

}
