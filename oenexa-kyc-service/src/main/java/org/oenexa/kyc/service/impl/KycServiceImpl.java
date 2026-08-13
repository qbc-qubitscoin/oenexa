package org.oenexa.kyc.service.impl;

import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.request.SubmitKycRequest;
import org.oenexa.kyc.dto.response.DocumentDto;
import org.oenexa.kyc.dto.response.KycProfileDto;
import org.oenexa.kyc.entity.DocumentEntity;
import org.oenexa.kyc.entity.DocumentType;
import org.oenexa.kyc.entity.KycProfileEntity;
import org.oenexa.kyc.entity.KycStatus;
import org.oenexa.kyc.repository.DocumentRepository;
import org.oenexa.kyc.repository.KycProfileRepository;
import org.oenexa.kyc.service.KycProviderService;
import org.oenexa.kyc.service.KycService;
import org.oenexa.kyc.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class KycServiceImpl implements KycService {

    private final KycProfileRepository kycProfileRepository;
    private final DocumentRepository documentRepository;
    private final KycProviderService kycProviderService;
    private final StorageService storageService;

    public KycServiceImpl(KycProfileRepository kycProfileRepository, 
                          DocumentRepository documentRepository, 
                          KycProviderService kycProviderService,
                          StorageService storageService) {
        this.kycProfileRepository = kycProfileRepository;
        this.documentRepository = documentRepository;
        this.kycProviderService = kycProviderService;
        this.storageService = storageService;
    }

    @Override
    @Transactional(readOnly = true)
    public KycProfileDto getKycStatus(UUID userId) {
        KycProfileEntity profile = kycProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    KycProfileEntity newProfile = new KycProfileEntity();
                    newProfile.setUserId(userId);
                    newProfile.setStatus(KycStatus.PENDING);
                    return kycProfileRepository.save(newProfile);
                });
        
        List<DocumentEntity> documents = documentRepository.findByKycProfileId(profile.getId());
        return mapToDto(profile, documents);
    }

    @Override
    @Transactional
    public KycProfileDto submitKyc(UUID userId, SubmitKycRequest request) {
        KycProfileEntity profile = kycProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC profile not found"));
        
        profile.setStatus(KycStatus.IN_REVIEW);
        KycProfileEntity updated = kycProfileRepository.save(profile);
        
        List<DocumentEntity> documents = documentRepository.findByKycProfileId(profile.getId());
        return mapToDto(updated, documents);
    }

    @Override
    @Transactional
    public DocumentDto uploadDocument(UUID userId, DocumentType documentType, MultipartFile file) {
        KycProfileEntity profile = kycProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC profile not found"));
        
        String documentUrl = storageService.uploadFile(file);
        
        DocumentEntity doc = new DocumentEntity();
        doc.setKycProfileId(profile.getId());
        doc.setDocumentType(documentType);
        doc.setDocumentUrl(documentUrl);
        
        DocumentEntity saved = documentRepository.save(doc);
        return new DocumentDto(saved.getId(), saved.getDocumentType(), saved.getDocumentUrl(), saved.getUploadedAt());
    }

    @Override
    @Transactional
    public KycProfileDto adminReview(UUID userId, AdminReviewRequest request) {
        // Delegate to Provider
        return kycProviderService.processAdminReview(userId, request);
    }

    private KycProfileDto mapToDto(KycProfileEntity profile, List<DocumentEntity> documents) {
        List<DocumentDto> docDtos = documents.stream()
                .map(doc -> new DocumentDto(doc.getId(), doc.getDocumentType(), doc.getDocumentUrl(), doc.getUploadedAt()))
                .collect(Collectors.toList());
                
        return new KycProfileDto(
                profile.getId(),
                profile.getUserId(),
                profile.getStatus(),
                profile.getRejectionReason(),
                profile.getProviderReferenceId(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                docDtos
        );
    }
}
