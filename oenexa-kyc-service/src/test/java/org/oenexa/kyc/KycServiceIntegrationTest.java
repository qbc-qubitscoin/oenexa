package org.oenexa.kyc;

import org.junit.jupiter.api.Test;
import org.oenexa.kyc.config.TestKafkaConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestKafkaConfig.class)
class KycServiceIntegrationTest {

    @Test
    void contextLoads() {
        // Verifies the application context loads cleanly without Testcontainers or Mockito
    }
}
