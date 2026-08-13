package org.oenexa.audit.dto; import lombok.*; @Data @Builder @NoArgsConstructor @AllArgsConstructor public class AuditLogResponse { private String id; private String action; }
