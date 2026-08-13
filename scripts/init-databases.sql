-- ═══════════════════════════════════════════════════
-- OENEXA™ — Database Initialization Script
-- Creates all per-service databases on first run
-- ═══════════════════════════════════════════════════

CREATE DATABASE IF NOT EXISTS identity_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS user_db     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS kyc_db      CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS wallet_db   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS trading_db  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS payment_db  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS security_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS notification_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS audit_db    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS banking_db  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant permissions to application user
CREATE USER IF NOT EXISTS 'oenexa'@'%' IDENTIFIED BY 'oenexa_password';

GRANT ALL PRIVILEGES ON identity_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON user_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON kyc_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON wallet_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON trading_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON payment_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON security_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON notification_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON audit_db.* TO 'oenexa'@'%';
GRANT ALL PRIVILEGES ON banking_db.* TO 'oenexa'@'%';

FLUSH PRIVILEGES;
