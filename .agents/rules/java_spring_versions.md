---
description: Enforces the use of Java 25 and Spring Boot 4.1.0 across the OENEXA project.
---

# Java and Spring Boot Versions

- Always use **Java 25** (JDK 25) for all modules in the OENEXA project.
- Always use **Spring Boot 4.1.0** for all Spring dependencies in the OENEXA project.
- **NEVER** downgrade these versions to fix build or runtime errors. If an error occurs, find a compatible workaround or configuration update that respects these versions.
- Ensure that `libs.versions.toml` and any `build.gradle.kts` files maintain these exact versions.
