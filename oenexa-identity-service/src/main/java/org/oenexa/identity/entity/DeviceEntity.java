package org.oenexa.identity.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="devices")
public class DeviceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String deviceName;
    private String deviceType;
    private String fingerprint;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private Boolean isTrusted;
    private LocalDateTime lastUsedAt;
    private LocalDateTime createdAt;
}
