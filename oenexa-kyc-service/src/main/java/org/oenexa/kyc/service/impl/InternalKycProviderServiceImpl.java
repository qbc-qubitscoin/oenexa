package org.oenexa.kyc.service.impl;

import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.response.DocumentDto;
import org.oenexa.kyc.dto.response.KycProfileDto;
import org.oenexa.kyc.entity.DocumentEntity;
import org.oenexa.kyc.entity.KycProfileEntity;
import org.oenexa.kyc.entity.KycStatus;
import org.oenexa.kyc.kafka.producer.KycEventProducer;
import org.oenexa.kyc.kafka.producer.KycStatusUpdatedEvent;
import org.oenexa.kyc.repository.DocumentRepository;
import org.oenexa.kyc.repository.KycProfileRepository;
import org.oenexa.kyc.service.KycProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InternalKycProviderServiceImpl implements KycProviderService {

    private final KycProfileRepository kycProfileRepository;
    private final DocumentRepository documentRepository;
    private final KycEventProducer kycEventProducer;

    public InternalKycProviderServiceImpl(KycProfileRepository kycProfileRepository, 
                                          DocumentRepository documentRepository, 
                                          KycEventProducer kycEventProducer) {
        this.kycProfileRepository = kycProfileRepository;
        this.documentRepository = documentRepository;
        this.kycEventProducer = kycEventProducer;
    }

    @Override
    @Transactional
    public KycProfileDto processAdminReview(UUID userId, AdminReviewRequest request) {
        KycProfileEntity profile = kycProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC profile not found"));
        
        profile.setStatus(request.status());
        if (request.status() == KycStatus.REJECTED) {
            profile.setRejectionReason(request.rejectionReason());
        } else {
            profile.setRejectionReason(null);
        }
        
        KycProfileEntity updated = kycProfileRepository.save(profile);
        
        // Publish event to Kafka
        kycEventProducer.publishKycStatusUpdated(new KycStatusUpdatedEvent(
            updated.getUserId(), updated.getStatus(), updated.getProviderReferenceId()
        ));
        
        List<DocumentEntity> documents = documentRepository.findByKycProfileId(profile.getId());
        return mapToDto(updated, documents);
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
