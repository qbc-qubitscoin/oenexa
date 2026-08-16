package org.oenexa.kyc.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.kyc.config.TestKafkaConfig;
import org.oenexa.kyc.dto.request.AdminReviewRequest;
import org.oenexa.kyc.dto.request.SubmitKycRequest;
import org.oenexa.kyc.dto.response.DocumentDto;
import org.oenexa.kyc.dto.response.KycProfileDto;
import org.oenexa.kyc.entity.DocumentType;
import org.oenexa.kyc.entity.KycStatus;
import org.oenexa.kyc.repository.DocumentRepository;
import org.oenexa.kyc.repository.KycProfileRepository;
import org.oenexa.kyc.service.impl.InternalKycProviderServiceImpl;
import org.oenexa.kyc.service.impl.KycServiceImpl;
import org.oenexa.kyc.service.impl.S3StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestKafkaConfig.class)
public class KycServiceImplTest {

    @Autowired
    private KycServiceImpl kycService;

    @Autowired
    private InternalKycProviderServiceImpl kycProviderService;

    @Autowired
    private S3StorageServiceImpl s3StorageService;

    @Autowired
    private KycProfileRepository kycProfileRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @BeforeEach
    @AfterEach
    void cleanUp() {
        documentRepository.deleteAll();
        kycProfileRepository.deleteAll();
    }

    @Test
    void testGetKycStatus_WhenNewProfile_ShouldCreateAndReturnPending() {
        UUID userId = UUID.randomUUID();

        KycProfileDto profile = kycService.getKycStatus(userId);

        assertThat(profile).isNotNull();
        assertThat(profile.userId()).isEqualTo(userId);
        assertThat(profile.status()).isEqualTo(KycStatus.PENDING);
        assertThat(profile.documents()).isEmpty();
    }

    @Test
    void testGetKycStatus_WhenExistingProfile_ShouldReturnExistingWithDocs() {
        UUID userId = UUID.randomUUID();
        kycService.getKycStatus(userId);

        MockMultipartFile file = new MockMultipartFile("file", "passport.jpg", "image/jpeg", "dummy content".getBytes());
        kycService.uploadDocument(userId, DocumentType.PASSPORT, file);

        KycProfileDto profile = kycService.getKycStatus(userId);

        assertThat(profile).isNotNull();
        assertThat(profile.userId()).isEqualTo(userId);
        assertThat(profile.documents()).hasSize(1);
        assertThat(profile.documents().get(0).documentType()).isEqualTo(DocumentType.PASSPORT);
    }

    @Test
    void testSubmitKyc_WhenProfileExists_ShouldSetInReview() {
        UUID userId = UUID.randomUUID();
        kycService.getKycStatus(userId);

        MockMultipartFile file = new MockMultipartFile("file", "passport.jpg", "image/jpeg", "dummy content".getBytes());
        kycService.uploadDocument(userId, DocumentType.PASSPORT, file);

        SubmitKycRequest request = new SubmitKycRequest("Documents uploaded");
        KycProfileDto result = kycService.submitKyc(userId, request);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(KycStatus.IN_REVIEW);
        assertThat(result.documents()).hasSize(1);
    }

    @Test
    void testSubmitKyc_WhenProfileNotFound_ShouldThrowException() {
        UUID nonExistentUserId = UUID.randomUUID();
        SubmitKycRequest request = new SubmitKycRequest("Documents uploaded");

        assertThatThrownBy(() -> kycService.submitKyc(nonExistentUserId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("KYC profile not found");
    }

    @Test
    void testUploadDocument_WhenProfileExists_ShouldSaveDocument() {
        UUID userId = UUID.randomUUID();
        kycService.getKycStatus(userId);

        MockMultipartFile file = new MockMultipartFile("file", "id.png", "image/png", "sample bytes".getBytes());
        DocumentDto doc = kycService.uploadDocument(userId, DocumentType.NATIONAL_ID, file);

        assertThat(doc).isNotNull();
        assertThat(doc.documentType()).isEqualTo(DocumentType.NATIONAL_ID);
        assertThat(doc.documentUrl()).isNotBlank();
    }

    @Test
    void testUploadDocument_WhenProfileNotFound_ShouldThrowException() {
        UUID nonExistentUserId = UUID.randomUUID();
        MockMultipartFile file = new MockMultipartFile("file", "id.png", "image/png", "sample bytes".getBytes());

        assertThatThrownBy(() -> kycService.uploadDocument(nonExistentUserId, DocumentType.NATIONAL_ID, file))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("KYC profile not found");
    }

    @Test
    void testAdminReview_Approved_ShouldClearRejectionReason() {
        UUID userId = UUID.randomUUID();
        kycService.getKycStatus(userId);

        MockMultipartFile file = new MockMultipartFile("file", "passport.jpg", "image/jpeg", "dummy content".getBytes());
        kycService.uploadDocument(userId, DocumentType.PASSPORT, file);

        AdminReviewRequest request = new AdminReviewRequest(KycStatus.VERIFIED, null);
        KycProfileDto result = kycService.adminReview(userId, request);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(KycStatus.VERIFIED);
        assertThat(result.rejectionReason()).isNull();
        assertThat(result.documents()).hasSize(1);
    }

    @Test
    void testAdminReview_Rejected_ShouldSetRejectionReason() {
        UUID userId = UUID.randomUUID();
        kycService.getKycStatus(userId);

        MockMultipartFile file = new MockMultipartFile("file", "passport.jpg", "image/jpeg", "dummy content".getBytes());
        kycService.uploadDocument(userId, DocumentType.PASSPORT, file);

        AdminReviewRequest request = new AdminReviewRequest(KycStatus.REJECTED, "Document illegible");
        KycProfileDto result = kycProviderService.processAdminReview(userId, request);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(KycStatus.REJECTED);
        assertThat(result.rejectionReason()).isEqualTo("Document illegible");
        assertThat(result.documents()).hasSize(1);
    }

    @Test
    void testAdminReview_WhenProfileNotFound_ShouldThrowException() {
        UUID nonExistentUserId = UUID.randomUUID();
        AdminReviewRequest request = new AdminReviewRequest(KycStatus.VERIFIED, null);

        assertThatThrownBy(() -> kycProviderService.processAdminReview(nonExistentUserId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("KYC profile not found");
    }

    @Test
    void testS3StorageService_UploadFile() {
        MockMultipartFile file = new MockMultipartFile("file", "doc.pdf", "application/pdf", "pdf bytes".getBytes());
        String url = s3StorageService.uploadFile(file);

        assertThat(url).isNotBlank().startsWith("https://mock-storage.oenexa.com/kyc/");
    }
}
