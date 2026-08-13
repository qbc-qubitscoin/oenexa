package org.oenexa.identity.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user_sessions")
public class UserSessionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String sessionToken;
    private Long deviceId;
    private String ipAddress;
    private String userAgent;
    private String geoLocation;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime lastActivity;
}
