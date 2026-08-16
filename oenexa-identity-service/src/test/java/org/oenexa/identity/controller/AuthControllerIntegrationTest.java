package org.oenexa.identity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.identity.dto.request.LoginRequest;
import org.oenexa.identity.dto.request.RegisterRequest;
import org.oenexa.identity.dto.response.LoginResponse;
import org.oenexa.identity.dto.response.RegisterResponse;
import org.oenexa.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        httpClient = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterAndLoginSuccessfully() throws Exception {
        // Register Request
        RegisterRequest registerRequest = new RegisterRequest(
                "John", "Doe", "john.doe@example.com", "Password123!"
        );

        HttpRequest reqRegister = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/api/v1/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(registerRequest)))
                .build();

        HttpResponse<String> resRegister = httpClient.send(reqRegister, HttpResponse.BodyHandlers.ofString());
        assertThat(resRegister.statusCode()).isEqualTo(201);
        RegisterResponse regResponse = objectMapper.readValue(resRegister.body(), RegisterResponse.class);
        assertThat(regResponse.uuid()).isNotBlank();

        // Login Request
        LoginRequest loginRequest = new LoginRequest("john.doe@example.com", "Password123!");
        HttpRequest reqLogin = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/api/v1/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(loginRequest)))
                .build();

        HttpResponse<String> resLogin = httpClient.send(reqLogin, HttpResponse.BodyHandlers.ofString());
        assertThat(resLogin.statusCode()).isEqualTo(200);
        LoginResponse loginResponse = objectMapper.readValue(resLogin.body(), LoginResponse.class);
        assertThat(loginResponse.accessToken()).isNotBlank();
    }
}
