# OENEXA™ — Open Economy Next Generation Exchange & Assets

[![CI Pipeline](https://github.com/oenexa/oenexa-platform/actions/workflows/ci.yml/badge.svg)](https://github.com/oenexa/oenexa-platform/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-26-orange.svg)](https://openjdk.org/projects/jdk/26/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)](LICENSE)

> **An open economy platform** — Next-generation digital asset exchange, payment infrastructure, and decentralized financial services built for the future.

## Architecture

OENEXA is a **19-module microservices** platform built with:

| Technology    | Version  | Purpose                          |
|---------------|----------|----------------------------------|
| Java          | 26       | Language runtime                 |
| Spring Boot   | 4.1.0    | Application framework            |
| Spring Cloud  | 2025.0.0 | Microservices infrastructure     |
| MySQL         | 9.3      | Primary transactional databases  |
| Redis         | 8+       | Caching, sessions, rate limiting |
| Apache Kafka  | 4.0      | Event streaming                  |
| Elasticsearch | 8.18     | Search & analytics               |
| Kubernetes    | 1.30+    | Container orchestration          |

## Quick Start

### Prerequisites
- Java 26+ (Temurin recommended)
- Docker & Docker Compose
- Gradle 8.14+ (or use `./gradlew`)

### 1. Start Infrastructure
```bash
docker compose up -d
```

### 2. Build All Modules
```bash
./gradlew build -x test
```

### 3. Run a Service
```bash
./gradlew :oenexa-identity-service:bootRun
```

### 4. View Project Structure
```bash
./gradlew projects
```

## Module Map

| Module                      | Port | Description                         |
|-----------------------------|------|-------------------------------------|
| oenexa-common               | —    | Shared DTOs, entities, utils, enums |
| oenexa-security-common      | —    | Shared JWT, security filters        |
| oenexa-api-gateway          | 8080 | Spring Cloud Gateway                |
| oenexa-identity-service     | 8081 | Auth, OAuth2, MFA                   |
| oenexa-user-service         | 8082 | User profiles & settings            |
| oenexa-kyc-service          | 8083 | KYC/AML verification                |
| oenexa-wallet-service       | 8084 | Multi-wallet management             |
| oenexa-trading-service      | 8085 | Spot/Margin/Futures trading         |
| oenexa-matching-engine      | 8086 | Low-latency order matching          |
| oenexa-payment-service      | 8087 | Card/Bank/SEPA/SWIFT                |
| oenexa-security-service     | 8088 | Fraud detection                     |
| oenexa-notification-service | 8089 | Email/SMS/Push/In-App               |
| oenexa-reporting-service    | 8090 | Financial reports                   |
| oenexa-audit-service        | 8091 | Immutable audit logs                |
| oenexa-market-data-service  | 8092 | Real-time price feeds               |
| oenexa-admin-service        | 8093 | Admin dashboard                     |
| oenexa-risk-engine          | 8094 | ML risk scoring                     |
| oenexa-analytics-service    | 8095 | Platform analytics                  |
| oenexa-banking-service      | 8096 | Banking & ledger                    |

## Documentation

- [Development Process Guide](OENEXA_Development_Process.md) — Complete step-by-step design guide
- [Architecture Diagrams](src/test/resources/OENEXA_Architecture_Diagrams.md)
- [Project Document](src/test/resources/OENEXA_Project_Document.md)
- [Development Roadmap](src/test/resources/OENEXA_Development_Roadmap.md)

## License

Copyright © 2026 OENEXA™. All Rights Reserved.
