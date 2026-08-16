package org.oenexa.user;

import org.junit.jupiter.api.Test;
import org.oenexa.user.config.TestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class UserServiceIntegrationTest {

    @Test
    void contextLoads() {
        // Verifies the user service application context loads cleanly
    }
}
