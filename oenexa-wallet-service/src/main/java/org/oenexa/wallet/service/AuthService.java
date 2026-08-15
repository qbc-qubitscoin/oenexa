package org.oenexa.wallet.service;

import lombok.RequiredArgsConstructor;
import org.oenexa.wallet.dto.AuthRequest;
import org.oenexa.wallet.dto.AuthResponse;
import org.oenexa.wallet.dto.RegisterRequest;
import org.oenexa.wallet.entity.Role;
import org.oenexa.wallet.entity.User;
import org.oenexa.wallet.repository.RoleRepository;
import org.oenexa.wallet.repository.UserRepository;
import org.oenexa.wallet.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(userRole))
                .isActive(true)
                .build();

        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtUtil.generateToken(user);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }
}
