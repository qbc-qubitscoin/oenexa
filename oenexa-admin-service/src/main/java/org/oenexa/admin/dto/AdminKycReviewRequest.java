package org.oenexa.admin.dto; import lombok.*; @Data @Builder @NoArgsConstructor @AllArgsConstructor public class AdminKycReviewRequest { private String kycId; private String status; }
