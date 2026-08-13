package org.oenexa.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="ledger_entries")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LedgerEntryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private LedgerType type;
    private String description;
    private LocalDateTime createdAt;

    public enum LedgerType { CREDIT,DEBIT }
}
