package org.oenexa.kyc.dto.response;

import org.oenexa.kyc.entity.KycStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record KycProfileDto(
    Long id,
    UUID userId,
    KycStatus status,
    String rejectionReason,
    String providerReferenceId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<DocumentDto> documents
) {}
