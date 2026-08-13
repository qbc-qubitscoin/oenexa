package org.oenexa.kyc.kafka.producer;

import java.util.UUID;
import org.oenexa.kyc.entity.KycStatus;

public record KycStatusUpdatedEvent(
    UUID userId,
    KycStatus status,
    String providerReferenceId
) {}
