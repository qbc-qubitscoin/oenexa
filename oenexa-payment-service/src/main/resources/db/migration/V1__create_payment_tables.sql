CREATE TABLE payments (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid                VARCHAR(36) NOT NULL UNIQUE,
    user_id             BIGINT NOT NULL,
    payment_type        ENUM('CARD','BANK_TRANSFER','SEPA','SWIFT','ACH','MOBILE_MONEY'),
    payment_direction   ENUM('INBOUND','OUTBOUND'),
    amount              DECIMAL(20,8) NOT NULL,
    currency            VARCHAR(3) NOT NULL,
    fee                 DECIMAL(20,8) DEFAULT 0,
    status              ENUM('INITIATED','PENDING','PROCESSING','COMPLETED','FAILED','CANCELLED','REFUNDED'),
    provider            VARCHAR(100),
    provider_reference  VARCHAR(255),
    metadata            JSON,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at        TIMESTAMP NULL,
    INDEX idx_user_payments (user_id, created_at),
    INDEX idx_status (status)
) ENGINE=InnoDB;

CREATE TABLE payment_methods (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    method_type     ENUM('CREDIT_CARD','DEBIT_CARD','BANK_ACCOUNT'),
    provider_token  VARCHAR(255),
    last_four       VARCHAR(4),
    brand           VARCHAR(50),
    is_default      BOOLEAN DEFAULT FALSE,
    status          ENUM('ACTIVE','EXPIRED','DISABLED'),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_methods (user_id, status)
) ENGINE=InnoDB;
