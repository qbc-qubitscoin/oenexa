package org.oenexa.wallet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.wallet.dto.AuthRequest;
import org.oenexa.wallet.dto.AuthResponse;
import org.oenexa.wallet.dto.RegisterRequest;
import org.oenexa.wallet.entity.User;
import org.oenexa.wallet.repository.UserRepository;
import org.oenexa.wallet.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegister_Success() {
        // Given
        RegisterRequest request = new RegisterRequest("test@test.com", "password123");

        // When
        AuthResponse response = authService.register(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("test@test.com", response.getEmail());
        
        User user = userRepository.findByEmail("test@test.com").orElseThrow();
        assertEquals("test@test.com", user.getEmail());
        assertTrue(jwtUtil.validateToken(response.getToken(), user));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Given
        RegisterRequest request1 = new RegisterRequest("test@test.com", "password123");
        authService.register(request1);
        
        RegisterRequest request2 = new RegisterRequest("test@test.com", "anotherpassword");

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.register(request2));
    }

    @Test
    void testLogin_Success() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("login@test.com", "password123");
        authService.register(registerRequest);
        
        AuthRequest loginRequest = new AuthRequest("login@test.com", "password123");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("login@test.com", response.getEmail());
    }

    @Test
    void testLogin_UserNotFound() {
        // Given
        AuthRequest loginRequest = new AuthRequest("unknown@test.com", "password123");

        // When & Then
        assertThrows(org.springframework.security.authentication.BadCredentialsException.class, () -> authService.login(loginRequest));
    }
    
    @Test
    void testLogin_WrongPassword() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest("login@test.com", "password123");
        authService.register(registerRequest);
        
        AuthRequest loginRequest = new AuthRequest("login@test.com", "wrongpassword");

        // When & Then
        assertThrows(org.springframework.security.authentication.BadCredentialsException.class, () -> authService.login(loginRequest));
    }
}
