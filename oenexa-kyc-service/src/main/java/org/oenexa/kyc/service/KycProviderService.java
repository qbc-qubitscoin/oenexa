package org.oenexa.kyc.service;

import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.response.KycProfileDto;
import java.util.UUID;

public interface KycProviderService {
    KycProfileDto processAdminReview(UUID userId, AdminReviewRequest request);
}
