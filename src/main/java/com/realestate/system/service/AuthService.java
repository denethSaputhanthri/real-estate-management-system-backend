package com.realestate.system.service;

import com.realestate.system.auth.AuthResponse;
import com.realestate.system.auth.LoginRequest;
import com.realestate.system.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
