package org.oenexa.kyc.dto.response;

import org.oenexa.kyc.entity.DocumentType;
import java.time.LocalDateTime;

public record DocumentDto(
    Long id,
    DocumentType documentType,
    String documentUrl,
    LocalDateTime uploadedAt
) {}
