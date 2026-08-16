package org.oenexa.identity.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.common.exception.BusinessException;
import org.oenexa.common.exception.DuplicateResourceException;
import org.oenexa.identity.dto.request.LoginRequest;
import org.oenexa.identity.dto.request.RegisterRequest;
import org.oenexa.identity.dto.response.LoginResponse;
import org.oenexa.identity.dto.response.RegisterResponse;
import org.oenexa.identity.entity.UserEntity;
import org.oenexa.identity.repository.UserRepository;
import org.oenexa.identity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthServiceImplIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterNewUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test.user@example.com", "Password123!");
        RegisterResponse response = authService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.uuid()).isNotBlank();
        assertThat(userRepository.existsByEmail("test.user@example.com")).isTrue();
    }

    @Test
    void shouldThrowWhenRegisteringDuplicateEmail() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test.user@example.com", "Password123!");
        authService.register(request);
        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Email already exists");
    }

    @Test
    void shouldVerifyEmailSuccessfully() {
        RegisterRequest request = new RegisterRequest("Test2", "User2", "test2.user@example.com", "Password123!");
        authService.register(request);
        authService.verifyEmail("test2.user@example.com", "123456");
        var user = userRepository.findByEmail("test2.user@example.com").orElseThrow();
        assertThat(user.getEmailVerified()).isTrue();
    }

    @Test
    void shouldThrowWhenVerifyingEmailNotFound() {
        assertThatThrownBy(() -> authService.verifyEmail("notfound@example.com", "123456"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void shouldThrowWhenEmailAlreadyVerified() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test@example.com", "Password123!");
        authService.register(request);
        authService.verifyEmail("test@example.com", "123456");
        assertThatThrownBy(() -> authService.verifyEmail("test@example.com", "123456"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Email is already verified");
    }

    @Test
    void shouldThrowWhenOtpIsInvalid() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test@example.com", "Password123!");
        authService.register(request);
        assertThatThrownBy(() -> authService.verifyEmail("test@example.com", "wrong"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid or expired OTP");
    }

    @Test
    void shouldLoginSuccessfully() {
        RegisterRequest request = new RegisterRequest("Test3", "User3", "test3.user@example.com", "Password123!");
        authService.register(request);
        var loginResponse = authService.login(new LoginRequest("test3.user@example.com", "Password123!"));
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.accessToken()).isNotBlank();
        assertThat(loginResponse.refreshToken()).isNotBlank();
    }

    @Test
    void shouldThrowWhenLoginUserNotFound() {
        assertThatThrownBy(() -> authService.login(new LoginRequest("notfound@example.com", "Pass")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid email or password");
    }

    @Test
    void shouldThrowWhenLoginInvalidPassword() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test@example.com", "Password123!");
        authService.register(request);
        assertThatThrownBy(() -> authService.login(new LoginRequest("test@example.com", "WrongPass")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid email or password");
    }

    @Test
    void shouldThrowWhenLoginInactiveAccount() {
        RegisterRequest request = new RegisterRequest("Test", "User", "test@example.com", "Password123!");
        authService.register(request);
        UserEntity user = userRepository.findByEmail("test@example.com").orElseThrow();
        user.setAccountStatus("INACTIVE");
        userRepository.save(user);

        assertThatThrownBy(() -> authService.login(new LoginRequest("test@example.com", "Password123!")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Account is not active");
    }

    @Test
    void shouldTestUnsupportedMethods() {
        assertThatThrownBy(() -> authService.verifyPhone("123", "123"))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() -> authService.enableMfa("123"))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() -> authService.verifyMfa("123", "123"))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() -> authService.refreshToken("invalid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid refresh token");

        RegisterRequest request = new RegisterRequest("Test4", "User4", "test4.user@example.com", "Password123!");
        authService.register(request);
        var loginResponse = authService.login(new LoginRequest("test4.user@example.com", "Password123!"));
        String validToken = loginResponse.refreshToken();

        assertThatThrownBy(() -> authService.refreshToken(validToken))
                .isInstanceOf(UnsupportedOperationException.class);

        // The logout method just logs, it doesn't throw.
        authService.logout("token");
    }
}
