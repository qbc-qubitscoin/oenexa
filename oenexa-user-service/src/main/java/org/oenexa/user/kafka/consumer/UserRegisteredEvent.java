package org.oenexa.user.kafka.consumer;

import java.util.UUID;

public record UserRegisteredEvent(
    UUID userId,
    String email,
    String phoneNumber
) {}
