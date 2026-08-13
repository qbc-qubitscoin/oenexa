package org.oenexa.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bank_accounts")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BankAccountEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum AccountType { CHECKING,SAVINGS,BUSINESS }
    public enum AccountStatus { PENDING,VERIFIED,REJECTED,CLOSED }
}
