# ═══════════════════════════════════════════════════
# OENEXA™ — Multi-stage Dockerfile Template
# Copy to each service module and adjust {SERVICE_NAME}
# ═══════════════════════════════════════════════════

# ── Stage 1: Build ──
FROM eclipse-temurin:26-jdk-alpine AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradle/libs.versions.toml ./

# Copy shared libraries
COPY oenexa-common oenexa-common
COPY oenexa-security-common oenexa-security-common

# Copy service source
ARG SERVICE_NAME
COPY ${SERVICE_NAME} ${SERVICE_NAME}

# Build the service
RUN chmod +x gradlew && \
    ./gradlew :${SERVICE_NAME}:bootJar -x test --no-daemon

# ── Stage 2: Runtime ──
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app

# Security: Create non-root user
RUN addgroup -g 1001 oenexa && \
    adduser -u 1001 -G oenexa -s /bin/sh -D oenexa

ARG SERVICE_NAME
COPY --from=builder /app/${SERVICE_NAME}/build/libs/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${SERVER_PORT:-8080}/actuator/health || exit 1

USER oenexa

EXPOSE ${SERVER_PORT:-8080}

ENTRYPOINT ["java", \
    "-XX:+UseZGC", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
