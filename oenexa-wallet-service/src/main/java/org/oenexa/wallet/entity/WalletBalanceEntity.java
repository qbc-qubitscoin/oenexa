package org.oenexa.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="wallet_balances")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WalletBalanceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private BigDecimal available;
    private BigDecimal locked;
    @Column(insertable = false, updatable = false)
    private BigDecimal total;
    private LocalDateTime lastUpdated;
    @Version
    private Long version;
}
