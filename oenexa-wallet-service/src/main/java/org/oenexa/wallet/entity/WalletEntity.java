package org.oenexa.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="wallets")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WalletEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private WalletType walletType;
    private String currency;
    
    @Column(precision = 30, scale = 8)
    private java.math.BigDecimal balance = java.math.BigDecimal.ZERO;
    
    @Column(precision = 30, scale = 8)
    private java.math.BigDecimal lockedBalance = java.math.BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    private WalletStatus status;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum WalletType { FIAT,CRYPTO,SPOT,MARGIN,FUTURES,EARN,REWARDS }
    public enum WalletStatus { ACTIVE,FROZEN,SUSPENDED,CLOSED }
}
