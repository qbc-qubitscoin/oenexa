package org.oenexa.identity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oenexa.common.exception.BusinessException;
import org.oenexa.common.exception.DuplicateResourceException;
import org.oenexa.identity.dto.request.LoginRequest;
import org.oenexa.identity.dto.request.RegisterRequest;
import org.oenexa.identity.dto.response.LoginResponse;
import org.oenexa.identity.dto.response.RegisterResponse;
import org.oenexa.identity.entity.UserEntity;
import org.oenexa.identity.repository.UserRepository;
import org.oenexa.identity.service.AuthService;
import org.oenexa.identity.service.NotificationService;
import org.oenexa.security.jwt.JwtProperties;
import org.oenexa.security.jwt.JwtTokenProvider;
import org.oenexa.security.model.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity user = UserEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .roles("ROLE_USER")
                .accountStatus("ACTIVE")
                .emailVerified(false)
                .phoneVerified(false)
                .mfaEnabled(false)
                .build();

        user = userRepository.save(user);

        // Send OTP (using mock or real implementation depending on active profile)
        notificationService.sendEmailOtp(user.getEmail(), "123456");

        return new RegisterResponse(user.getUuid(), "User registered successfully. Please verify your email.");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessException("Invalid email or password");
        }

        if (!"ACTIVE".equals(user.getAccountStatus())) {
            throw new BusinessException("Account is not active");
        }

        UserPrincipal principal = UserPrincipal.builder()
                .id(user.getId())
                .uuid(user.getUuid())
                .email(user.getEmail())
                .build();

        String accessToken = jwtTokenProvider.generateAccessToken(principal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtProperties.getAccessTokenExpiration() / 1000
        );
    }

    @Override
    public void verifyEmail(String email, String otp) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new BusinessException("Email is already verified");
        }

        /*
         * TODO [FUTURE_IMPLEMENTATION]: Integrate with Redis for actual OTP validation.
         * 
         * Procedure to implement in future:
         * 1. Add spring-boot-starter-data-redis dependency.
         * 2. Inject RedisTemplate<String, String> redisTemplate into AuthServiceImpl.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * String storedOtp = redisTemplate.opsForValue().get("OTP_EMAIL_" + email);
         * if (storedOtp == null) {
         *     throw new BusinessException("OTP expired or not requested");
         * }
         * if (!storedOtp.equals(otp)) {
         *     throw new BusinessException("Invalid OTP");
         * }
         * redisTemplate.delete("OTP_EMAIL_" + email);
         */
        // Simulating success if OTP is "123456" for now.
        if (!"123456".equals(otp)) {
            throw new BusinessException("Invalid or expired OTP");
        }

        user.setEmailVerified(true);
        userRepository.save(user);
        log.info("Email verified successfully for user: {}", email);
    }

    @Override
    public void verifyPhone(String phone, String otp) {
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Implement phone verification logic.
         * 
         * Procedure to implement in future:
         * 1. Add Redis configuration and template.
         * 2. Implement the SMS sending logic using the NotificationService.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * UserEntity user = userRepository.findByPhone(phone)
         *         .orElseThrow(() -> new BusinessException("User not found"));
         * if (Boolean.TRUE.equals(user.getPhoneVerified())) {
         *     throw new BusinessException("Phone is already verified");
         * }
         * 
         * String storedOtp = redisTemplate.opsForValue().get("OTP_PHONE_" + phone);
         * if (storedOtp == null || !storedOtp.equals(otp)) {
         *     throw new BusinessException("Invalid or expired OTP");
         * }
         * 
         * redisTemplate.delete("OTP_PHONE_" + phone);
         * user.setPhoneVerified(true);
         * userRepository.save(user);
         */
        log.warn("Phone verification invoked but not fully implemented yet.");
        throw new UnsupportedOperationException("Phone verification requires Redis and SMS provider integration (e.g. Twilio).");
    }

    @Override
    public void enableMfa(String userId) {
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Implement MFA setup.
         * 
         * Procedure to implement in future:
         * 1. Add TOTP library dependency (e.g. implementation("com.eatthepath:java-otp:0.4.0")).
         * 2. Use it to generate secrets and URI for QR code generation.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * UserEntity user = userRepository.findById(userId)
         *         .orElseThrow(() -> new BusinessException("User not found"));
         * 
         * byte[] secretKey = generateSecretKey(); // e.g. using KeyGenerator
         * String base32Secret = Base32.encode(secretKey);
         * user.setMfaSecret(base32Secret);
         * userRepository.save(user);
         * 
         * String qrCodeUri = String.format("otpauth://totp/Oenexa:%s?secret=%s&issuer=Oenexa", 
         *         user.getEmail(), base32Secret);
         * // Return qrCodeUri in response for frontend to render QR code
         */
        log.warn("MFA setup invoked but not fully implemented yet.");
        throw new UnsupportedOperationException("MFA setup requires TOTP library integration.");
    }

    @Override
    public void verifyMfa(String userId, String code) {
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Implement MFA verification.
         * 
         * Procedure to implement in future:
         * 1. Use the same TOTP library from MFA setup.
         * 2. Validate the TOTP code against the saved secret.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * UserEntity user = userRepository.findById(userId)
         *         .orElseThrow(() -> new BusinessException("User not found"));
         * 
         * if (user.getMfaSecret() == null) {
         *     throw new BusinessException("MFA is not enabled for this user");
         * }
         * 
         * TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
         * int expectedCode = totp.generateOneTimePassword(decodeBase32(user.getMfaSecret()), Instant.now());
         * 
         * if (Integer.parseInt(code) != expectedCode) {
         *     throw new BusinessException("Invalid MFA code");
         * }
         * // Proceed to generate and return JWT tokens (like in login)
         */
        log.warn("MFA verification invoked but not fully implemented yet.");
        throw new UnsupportedOperationException("MFA verification requires TOTP library integration.");
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Implement token refresh logic.
         * 
         * Procedure to implement in future:
         * 1. Ensure JwtTokenProvider can extract all required claims.
         * 2. Add logic to query Redis for blacklisted tokens.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * if (!jwtTokenProvider.validateToken(refreshToken)) {
         *     throw new BusinessException("Invalid refresh token");
         * }
         * 
         * String jti = jwtTokenProvider.extractClaim(refreshToken, Claims::getId);
         * if (Boolean.TRUE.equals(redisTemplate.hasKey("BL_TOKEN_" + jti))) {
         *     throw new BusinessException("Refresh token is revoked");
         * }
         * 
         * UserPrincipal principal = jwtTokenProvider.extractPrincipal(refreshToken);
         * String newAccessToken = jwtTokenProvider.generateAccessToken(principal);
         * 
         * return new LoginResponse(
         *     newAccessToken,
         *     refreshToken,
         *     "Bearer",
         *     jwtProperties.getAccessTokenExpiration() / 1000
         * );
         */
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }
        
        log.warn("Refresh token invoked but not fully implemented yet.");
        throw new UnsupportedOperationException("Refresh token flow requires parsing UserPrincipal from token claims.");
    }

    @Override
    public void logout(String token) {
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Implement secure logout.
         * 
         * Procedure to implement in future:
         * 1. Add Redis template support.
         * 2. Extract JTI (JWT ID) or signature to blacklist.
         * 3. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * String jti = jwtTokenProvider.extractClaim(token, Claims::getId);
         * long remainingTime = jwtTokenProvider.extractClaim(token, Claims::getExpiration).getTime() - System.currentTimeMillis();
         * 
         * if (remainingTime > 0) {
         *     redisTemplate.opsForValue().set("BL_TOKEN_" + jti, "revoked", remainingTime, TimeUnit.MILLISECONDS);
         * }
         * log.info("Token {} blacklisted successfully", jti);
         */
        log.info("Logout request received. Token invalidation will be handled via Redis blacklist in future release.");
    }
}
