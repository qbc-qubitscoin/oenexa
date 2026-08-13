package org.oenexa.kyc.dto.request;

import org.oenexa.kyc.entity.KycStatus;

public record AdminReviewRequest(
    KycStatus status,
    String rejectionReason
) {}
