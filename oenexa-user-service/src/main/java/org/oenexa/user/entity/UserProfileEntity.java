package org.oenexa.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {

    // Getters and Setters
    @Id
    private UUID userId;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String postalCode;

    @Column(columnDefinition = "json")
    private String preferences;

    @Enumerated(EnumType.STRING)
    private KycLevel kycLevel = KycLevel.NONE;

    // JPA requires no-args constructor
    public UserProfileEntity() {}

}
