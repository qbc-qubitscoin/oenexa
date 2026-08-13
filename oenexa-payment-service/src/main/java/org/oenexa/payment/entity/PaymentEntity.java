package org.oenexa.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private PaymentDirection paymentDirection;
    private BigDecimal amount;
    private String currency;
    private BigDecimal fee;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String provider;
    private String providerReference;
    @Column(columnDefinition = "JSON")
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public enum PaymentType { CARD,BANK_TRANSFER,SEPA,SWIFT,ACH,MOBILE_MONEY }
    public enum PaymentDirection { INBOUND,OUTBOUND }
    public enum PaymentStatus { INITIATED,PENDING,PROCESSING,COMPLETED,FAILED,CANCELLED,REFUNDED }
}
