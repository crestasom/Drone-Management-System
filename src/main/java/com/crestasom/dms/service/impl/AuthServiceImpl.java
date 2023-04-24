package com.crestasom.dms.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crestasom.dms.exception.InvalidCredentialException;
import com.crestasom.dms.model.User;
import com.crestasom.dms.model.request.AuthRequest;
import com.crestasom.dms.model.response.AuthResponse;
import com.crestasom.dms.security.JwtTokenProvider;
import com.crestasom.dms.service.AuthService;
import com.crestasom.dms.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private UserService userService;
	private PasswordEncoder encoder;
	private JwtTokenProvider jUtil;

	@Override
	public AuthResponse checkLogin(AuthRequest authRequest) {
		User userDetails = userService.findByUserName(authRequest.getUsername())
				.orElseThrow(() -> new InvalidCredentialException("Incorrect username or password"));
		if (!checkPassword(authRequest.getPassword(), userDetails.getPassword())) {
			throw new InvalidCredentialException("Incorrect username or password");
		}
		final String jwt = jUtil.generateToken(userDetails);
		AuthResponse res = new AuthResponse();
		res.setRespCode(200);
		res.setRespDesc("Success");
		res.setJwt(jwt);
		return res;
	}

	private boolean checkPassword(String reqPassword, String dbPassword) {
		return encoder.matches(reqPassword, dbPassword);
	}
}
