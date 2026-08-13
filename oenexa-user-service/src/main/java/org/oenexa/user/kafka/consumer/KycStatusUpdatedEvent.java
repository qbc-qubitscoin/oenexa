package org.oenexa.user.kafka.consumer;

import java.util.UUID;
import org.oenexa.user.entity.KycLevel;

public record KycStatusUpdatedEvent(
    UUID userId,
    String status,
    String providerReferenceId
) {}
