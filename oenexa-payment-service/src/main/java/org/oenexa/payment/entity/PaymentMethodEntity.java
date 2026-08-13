package org.oenexa.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="payment_methods")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentMethodEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private MethodType methodType;
    private String providerToken;
    private String lastFour;
    private String brand;
    private Boolean isDefault;
    @Enumerated(EnumType.STRING)
    private MethodStatus status;
    private LocalDateTime createdAt;

    public enum MethodType { CREDIT_CARD,DEBIT_CARD,BANK_ACCOUNT }
    public enum MethodStatus { ACTIVE,EXPIRED,DISABLED }
}
