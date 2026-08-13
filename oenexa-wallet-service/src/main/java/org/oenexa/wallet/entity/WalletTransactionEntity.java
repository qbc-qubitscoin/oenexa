package org.oenexa.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="wallet_transactions")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WalletTransactionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private Long walletId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal fee;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String referenceId;
    private String referenceType;
    private String description;
    @Column(columnDefinition = "JSON")
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public enum TransactionType { DEPOSIT,WITHDRAWAL,TRANSFER_IN,TRANSFER_OUT,TRADE_BUY,TRADE_SELL,FEE,STAKE,UNSTAKE,LENDING,BORROWING,REWARD,REFUND }
    public enum TransactionStatus { PENDING,PROCESSING,COMPLETED,FAILED,CANCELLED,REVERSED }
}
