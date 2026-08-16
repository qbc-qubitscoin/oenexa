package org.oenexa.user.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.user.config.TestConfig;
import org.oenexa.user.dto.request.UpdatePreferencesRequest;
import org.oenexa.user.dto.request.UpdateUserProfileRequest;
import org.oenexa.user.dto.response.UserProfileDto;
import org.oenexa.user.entity.UserProfileEntity;
import org.oenexa.user.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UUID testUserId;

    @BeforeEach
    void setUp() {
        userProfileRepository.deleteAll();

        testUserId = UUID.randomUUID();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserId(testUserId);
        entity.setFirstName("John");
        entity.setLastName("Doe");
        userProfileRepository.save(entity);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                testUserId.toString(),
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        userProfileRepository.deleteAll();
    }

    @Test
    void testGetMyProfile() {
        ResponseEntity<UserProfileDto> response = userController.getMyProfile();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().userId()).isEqualTo(testUserId);
        assertThat(response.getBody().firstName()).isEqualTo("John");
    }

    @Test
    void testUpdateMyProfile() {
        UpdateUserProfileRequest request = new UpdateUserProfileRequest(
                "Johnny", "Doe", LocalDate.of(1995, 1, 1), "456 Avenue", "Suite 1", "City", "Country", "10001"
        );
        ResponseEntity<UserProfileDto> response = userController.updateMyProfile(request);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().firstName()).isEqualTo("Johnny");
    }

    @Test
    void testUpdateMyPreferences() {
        UpdatePreferencesRequest request = new UpdatePreferencesRequest("{\"notifications\":true}");
        ResponseEntity<UserProfileDto> response = userController.updateMyPreferences(request);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().preferences()).isEqualTo("{\"notifications\":true}");
    }
}
