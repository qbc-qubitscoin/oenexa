package org.oenexa.user.dto.response;

import org.oenexa.user.entity.KycLevel;
import java.time.LocalDate;
import java.util.UUID;

public record UserProfileDto(
    UUID userId,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String addressLine1,
    String addressLine2,
    String city,
    String country,
    String postalCode,
    String preferences,
    KycLevel kycLevel
) {}
