# Strict Project Quality & Architecture Rules

1. **Tech Stack & Versions**: 
   - ALWAYS use the latest LTS versions for all technologies: Java (JDK 25 LTS), Node (Node 24 LTS), Go (1.26), and Spring Boot (Latest 3.4+).
2. **Code Originality**:
   - Do NOT use third-party code logic unless necessary; prioritize native, custom-built logic perfectly tailored to the framework.
   - Do NOT duplicate code. Follow DRY strictly.
   - Do NOT use deprecated APIs or libraries. Keep the codebase modern.
3. **Test Coverage & Quality**:
   - Unit tests MUST cover 100% of the codebase.
   - Jacoco MUST be used for test coverage metrics and CI enforcement.
   - SonarQube MUST be configured for continuous code review, static analysis, and security scanning.
   - NEVER use Mockito or any third-party mocking frameworks. Use native Spring capabilities, manual stubs/fakes, or in-memory databases (like H2) for testing.
