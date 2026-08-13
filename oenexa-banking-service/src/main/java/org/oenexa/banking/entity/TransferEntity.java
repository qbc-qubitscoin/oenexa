package org.oenexa.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transfers")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TransferEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long bankAccountId;
    private Long beneficiaryId;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TransferDirection direction;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum TransferDirection { INBOUND,OUTBOUND }
    public enum TransferStatus { PENDING,PROCESSING,COMPLETED,FAILED }
}
