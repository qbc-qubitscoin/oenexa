package org.oenexa.wallet.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.wallet.dto.AuthResponse;
import org.oenexa.wallet.dto.RegisterRequest;
import org.oenexa.wallet.entity.User;
import org.oenexa.wallet.entity.WalletEntity;
import org.oenexa.wallet.repository.UserRepository;
import org.oenexa.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WalletControllerTest {

    @org.springframework.boot.test.web.server.LocalServerPort
    private int port;

    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testGetWallets() throws Exception {
        // Create user and get token
        RegisterRequest registerRequest = new RegisterRequest("wallet-user@test.com", "password");
        
        HttpRequest authReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(registerRequest)))
                .build();

        HttpResponse<String> authResp = httpClient.send(authReq, HttpResponse.BodyHandlers.ofString());
        AuthResponse authResponse = objectMapper.readValue(authResp.body(), AuthResponse.class);
        String token = authResponse.getToken();

        User user = userRepository.findByEmail("wallet-user@test.com").orElseThrow();
        Long userId = user.getId();

        // Given
        walletService.addBalance(userId, "USD", BigDecimal.valueOf(100));

        HttpRequest getReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/wallets/" + userId))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(getReq, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        List<WalletEntity> wallets = objectMapper.readValue(response.body(), new TypeReference<List<WalletEntity>>(){});
        assertFalse(wallets.isEmpty());
        assertEquals("USD", wallets.get(0).getCurrency());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(wallets.get(0).getBalance()));
    }
}
