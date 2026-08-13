CREATE TABLE kyc_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    status ENUM('NONE','PENDING','TIER1','TIER2','TIER3','REJECTED') DEFAULT 'NONE',
    current_tier ENUM('NONE','TIER1','TIER2','TIER3') DEFAULT 'NONE',
    residential_address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(3),
    nationality VARCHAR(3),
    id_number VARCHAR(100),
    tax_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB;

CREATE TABLE documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    document_type ENUM('PASSPORT','NATIONAL_ID','DRIVERS_LICENSE','PROOF_OF_ADDRESS'),
    file_url VARCHAR(255) NOT NULL,
    status ENUM('PENDING','VERIFIED','REJECTED') DEFAULT 'PENDING',
    rejection_reason VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    verified_at TIMESTAMP NULL,
    INDEX idx_user_doc (user_id)
) ENGINE=InnoDB;

CREATE TABLE verification_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    action VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    verified_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_history (user_id)
) ENGINE=InnoDB;
