package org.oenexa.kyc.controller;

import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.request.SubmitKycRequest;
import org.oenexa.kyc.dto.response.DocumentDto;
import org.oenexa.kyc.dto.response.KycProfileDto;
import org.oenexa.kyc.service.KycService;
import org.oenexa.security.common.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kyc")
public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @GetMapping("/status")
    public ResponseEntity<KycProfileDto> getKycStatus() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(kycService.getKycStatus(userId));
    }

    @PostMapping("/submit")
    public ResponseEntity<KycProfileDto> submitKyc(@Validated @RequestBody SubmitKycRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(kycService.submitKyc(userId, request));
    }

    @PostMapping(value = "/documents", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDto> uploadDocument(
            @RequestParam("documentType") org.oenexa.kyc.entity.DocumentType documentType,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(kycService.uploadDocument(userId, documentType, file));
    }

    // This endpoint should ideally be protected by an Admin role check in SecurityConfig
    @PostMapping("/admin/review/{userId}")
    public ResponseEntity<KycProfileDto> adminReview(
            @PathVariable UUID userId,
            @Validated @RequestBody AdminReviewRequest request) {
        return ResponseEntity.ok(kycService.adminReview(userId, request));
    }
}
