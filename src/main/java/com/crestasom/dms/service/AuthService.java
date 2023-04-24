package com.crestasom.dms.service;

import com.crestasom.dms.model.request.AuthRequest;
import com.crestasom.dms.model.response.AuthResponse;

public interface AuthService {
	public AuthResponse checkLogin(AuthRequest authRequest);

}
