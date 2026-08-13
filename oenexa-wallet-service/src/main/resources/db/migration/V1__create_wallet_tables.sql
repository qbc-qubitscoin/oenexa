CREATE TABLE wallets (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid            VARCHAR(36) NOT NULL UNIQUE,
    user_id         BIGINT NOT NULL,
    wallet_type     ENUM('FIAT','CRYPTO','SPOT','MARGIN','FUTURES','EARN','REWARDS'),
    currency        VARCHAR(10) NOT NULL,
    status          ENUM('ACTIVE','FROZEN','SUSPENDED','CLOSED'),
    is_default      BOOLEAN DEFAULT FALSE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_wallet (user_id, wallet_type, currency),
    INDEX idx_user_wallets (user_id, status)
) ENGINE=InnoDB;

CREATE TABLE wallet_balances (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallet_id       BIGINT NOT NULL,
    available       DECIMAL(36,18) NOT NULL DEFAULT 0,
    locked          DECIMAL(36,18) NOT NULL DEFAULT 0,
    total           DECIMAL(36,18) GENERATED ALWAYS AS (available + locked) STORED,
    last_updated    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version         BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (wallet_id) REFERENCES wallets(id),
    UNIQUE KEY uk_wallet_balance (wallet_id)
) ENGINE=InnoDB;

CREATE TABLE wallet_transactions (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid                VARCHAR(36) NOT NULL UNIQUE,
    wallet_id           BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    transaction_type    ENUM('DEPOSIT','WITHDRAWAL','TRANSFER_IN','TRANSFER_OUT',
                             'TRADE_BUY','TRADE_SELL','FEE','STAKE','UNSTAKE',
                             'LENDING','BORROWING','REWARD','REFUND'),
    amount              DECIMAL(36,18) NOT NULL,
    fee                 DECIMAL(36,18) DEFAULT 0,
    currency            VARCHAR(10) NOT NULL,
    status              ENUM('PENDING','PROCESSING','COMPLETED','FAILED','CANCELLED','REVERSED'),
    reference_id        VARCHAR(255),
    reference_type      VARCHAR(50),
    description         VARCHAR(500),
    metadata            JSON,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at        TIMESTAMP NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallets(id),
    INDEX idx_user_tx (user_id, created_at),
    INDEX idx_wallet_tx (wallet_id, created_at),
    INDEX idx_status (status)
) ENGINE=InnoDB;

CREATE TABLE wallet_addresses (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallet_id       BIGINT NOT NULL,
    blockchain      VARCHAR(50) NOT NULL,
    address         VARCHAR(255) NOT NULL,
    address_tag     VARCHAR(100),
    is_internal     BOOLEAN DEFAULT FALSE,
    label           VARCHAR(100),
    status          ENUM('ACTIVE','DISABLED'),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_id) REFERENCES wallets(id),
    INDEX idx_address (address, blockchain)
) ENGINE=InnoDB;
