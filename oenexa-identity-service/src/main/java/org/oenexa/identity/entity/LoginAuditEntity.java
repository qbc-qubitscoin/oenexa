package org.oenexa.identity.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="login_audit")
public class LoginAuditEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String loginStatus;
    private String ipAddress;
    private String userAgent;
    private Long deviceId;
    private String geoLocation;
    private String failureReason;
    private BigDecimal riskScore;
    private LocalDateTime createdAt;
}
