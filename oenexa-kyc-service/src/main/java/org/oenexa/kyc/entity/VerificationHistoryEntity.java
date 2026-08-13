package org.oenexa.kyc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="verification_history")
public class VerificationHistoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String action;
    private String status;
    private String notes;
    private Long verifiedBy;
    private LocalDateTime createdAt;
}
