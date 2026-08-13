package org.oenexa.kyc.service;

import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.request.SubmitKycRequest;
import org.oenexa.kyc.dto.response.DocumentDto;
import org.oenexa.kyc.dto.response.KycProfileDto;

import java.util.UUID;

public interface KycService {
    KycProfileDto getKycStatus(UUID userId);
    KycProfileDto submitKyc(UUID userId, SubmitKycRequest request);
    DocumentDto uploadDocument(UUID userId, org.oenexa.kyc.entity.DocumentType documentType, org.springframework.web.multipart.MultipartFile file);
    KycProfileDto adminReview(UUID userId, AdminReviewRequest request);}
