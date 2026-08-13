package org.oenexa.identity.service;

import org.oenexa.identity.dto.request.LoginRequest;
import org.oenexa.identity.dto.request.RegisterRequest;
import org.oenexa.identity.dto.response.LoginResponse;
import org.oenexa.identity.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void verifyEmail(String email, String otp);
    void verifyPhone(String phone, String otp);
    void enableMfa(String userId);
    void verifyMfa(String userId, String code);
    LoginResponse refreshToken(String refreshToken);
    void logout(String token);
}
