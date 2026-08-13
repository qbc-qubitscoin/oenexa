CREATE TABLE users (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid                VARCHAR(36) NOT NULL UNIQUE,
    first_name          VARCHAR(100) NOT NULL,
    last_name           VARCHAR(100) NOT NULL,
    email               VARCHAR(255) NOT NULL UNIQUE,
    phone_number        VARCHAR(20) NOT NULL UNIQUE,
    password_hash       VARCHAR(255) NOT NULL,
    email_verified      BOOLEAN DEFAULT FALSE,
    phone_verified      BOOLEAN DEFAULT FALSE,
    mfa_enabled         BOOLEAN DEFAULT FALSE,
    mfa_secret          VARCHAR(255),
    kyc_status          ENUM('NONE','PENDING','TIER1','TIER2','TIER3','REJECTED'),
    account_status      ENUM('PENDING','ACTIVE','SUSPENDED','LOCKED','CLOSED'),
    account_tier        ENUM('BASIC','STANDARD','PREMIUM','VIP','INSTITUTIONAL'),
    country_code        VARCHAR(3),
    timezone            VARCHAR(50),
    language            VARCHAR(10) DEFAULT 'en',
    last_login_at       TIMESTAMP NULL,
    last_login_ip       VARCHAR(45),
    failed_login_count  INT DEFAULT 0,
    locked_until        TIMESTAMP NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_phone (phone_number),
    INDEX idx_status (account_status)
) ENGINE=InnoDB;

CREATE TABLE roles (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE permissions (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL UNIQUE,
    resource    VARCHAR(100) NOT NULL,
    action      VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_resource_action (resource, action)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    granted_by BIGINT,
    granted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB;

CREATE TABLE user_sessions (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    session_token   VARCHAR(255) NOT NULL UNIQUE,
    device_id       BIGINT,
    ip_address      VARCHAR(45),
    user_agent      TEXT,
    geo_location    VARCHAR(100),
    is_active       BOOLEAN DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at      TIMESTAMP NOT NULL,
    last_activity   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_session_token (session_token),
    INDEX idx_user_active (user_id, is_active)
) ENGINE=InnoDB;

CREATE TABLE login_audit (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    login_status    ENUM('SUCCESS','FAILED','LOCKED','MFA_REQUIRED'),
    ip_address      VARCHAR(45),
    user_agent      TEXT,
    device_id       BIGINT,
    geo_location    VARCHAR(100),
    failure_reason  VARCHAR(255),
    risk_score      DECIMAL(5,4),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_login (user_id, created_at)
) ENGINE=InnoDB;

CREATE TABLE devices (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT NOT NULL,
    device_name     VARCHAR(100),
    device_type     ENUM('DESKTOP','MOBILE','TABLET','API'),
    fingerprint     VARCHAR(255) NOT NULL,
    os_name         VARCHAR(50),
    os_version      VARCHAR(50),
    browser_name    VARCHAR(50),
    browser_version VARCHAR(50),
    is_trusted      BOOLEAN DEFAULT FALSE,
    last_used_at    TIMESTAMP NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_device (user_id, fingerprint)
) ENGINE=InnoDB;
