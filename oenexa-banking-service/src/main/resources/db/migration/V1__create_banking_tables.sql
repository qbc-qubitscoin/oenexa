CREATE TABLE bank_accounts (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    account_number  VARCHAR(50) NOT NULL,
    routing_number  VARCHAR(50),
    bank_name       VARCHAR(100) NOT NULL,
    account_type    ENUM('CHECKING','SAVINGS','BUSINESS'),
    currency        VARCHAR(3) NOT NULL,
    status          ENUM('PENDING','VERIFIED','REJECTED','CLOSED'),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE beneficiaries (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    name            VARCHAR(100) NOT NULL,
    account_number  VARCHAR(50) NOT NULL,
    bank_name       VARCHAR(100) NOT NULL,
    swift_code      VARCHAR(20),
    status          ENUM('ACTIVE','INACTIVE'),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE transfers (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    bank_account_id BIGINT,
    beneficiary_id  BIGINT,
    amount          DECIMAL(20,8) NOT NULL,
    currency        VARCHAR(3) NOT NULL,
    direction       ENUM('INBOUND','OUTBOUND'),
    status          ENUM('PENDING','PROCESSING','COMPLETED','FAILED'),
    reference       VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE ledger_entries (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_id      BIGINT NOT NULL,
    amount          DECIMAL(20,8) NOT NULL,
    currency        VARCHAR(3) NOT NULL,
    type            ENUM('CREDIT','DEBIT'),
    description     VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;
