package org.oenexa.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, updatable = false, nullable = false)
    private String uuid;
    
    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phoneNumber;
    
    @Column(nullable = false)
    private String passwordHash;
    
    private String roles; // Comma separated roles e.g. "ROLE_USER,ROLE_ADMIN"
    
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean mfaEnabled;
    private String mfaSecret;
    private String kycStatus;
    private String accountStatus;
    private String accountTier;
    private String countryCode;
    private String timezone;
    private String language;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;
    private Integer failedLoginCount;
    private LocalDateTime lockedUntil;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
