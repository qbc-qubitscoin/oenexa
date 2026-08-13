package org.oenexa.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="wallet_addresses")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WalletAddressEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private String blockchain;
    private String address;
    private String addressTag;
    private Boolean isInternal;
    private String label;
    @Enumerated(EnumType.STRING)
    private AddressStatus status;
    private LocalDateTime createdAt;

    public enum AddressStatus { ACTIVE,DISABLED }
}
