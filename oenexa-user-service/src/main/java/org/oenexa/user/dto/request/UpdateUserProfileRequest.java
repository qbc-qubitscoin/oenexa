package org.oenexa.user.dto.request;

import java.time.LocalDate;

public record UpdateUserProfileRequest(
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String addressLine1,
    String addressLine2,
    String city,
    String country,
    String postalCode
) {}
