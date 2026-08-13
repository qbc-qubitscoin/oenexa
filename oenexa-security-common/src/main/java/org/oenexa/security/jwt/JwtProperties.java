package org.oenexa.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oenexa.jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenExpiration = 900000;
    private long refreshTokenExpiration = 604800000;
    private String issuer = "oenexa";
}
