package org.oenexa.banking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="beneficiaries")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BeneficiaryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String accountNumber;
    private String bankName;
    private String swiftCode;
    @Enumerated(EnumType.STRING)
    private BeneficiaryStatus status;
    private LocalDateTime createdAt;

    public enum BeneficiaryStatus { ACTIVE,INACTIVE }
}
