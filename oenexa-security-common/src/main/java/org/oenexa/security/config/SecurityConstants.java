package org.oenexa.security.config;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/actuator/health",
            "/v3/api-docs/**"
    };
}
