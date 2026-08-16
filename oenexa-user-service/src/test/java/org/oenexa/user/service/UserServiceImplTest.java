package org.oenexa.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oenexa.user.config.TestConfig;
import org.oenexa.user.dto.request.UpdatePreferencesRequest;
import org.oenexa.user.dto.request.UpdateUserProfileRequest;
import org.oenexa.user.dto.response.UserProfileDto;
import org.oenexa.user.entity.KycLevel;
import org.oenexa.user.entity.UserProfileEntity;
import org.oenexa.user.repository.UserProfileRepository;
import org.oenexa.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @BeforeEach
    @AfterEach
    void cleanUp() {
        userProfileRepository.deleteAll();
    }

    @Test
    void testGetUserProfile_WhenProfileExists() {
        UUID userId = UUID.randomUUID();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserId(userId);
        entity.setFirstName("Alice");
        entity.setLastName("Wonderland");
        entity.setKycLevel(KycLevel.BASIC);
        userProfileRepository.save(entity);

        UserProfileDto profile = userService.getUserProfile(userId);

        assertThat(profile).isNotNull();
        assertThat(profile.userId()).isEqualTo(userId);
        assertThat(profile.firstName()).isEqualTo("Alice");
        assertThat(profile.lastName()).isEqualTo("Wonderland");
        assertThat(profile.kycLevel()).isEqualTo(KycLevel.BASIC);
    }

    @Test
    void testGetUserProfile_WhenNotFound_ShouldThrowException() {
        UUID userId = UUID.randomUUID();

        assertThatThrownBy(() -> userService.getUserProfile(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User profile not found");
    }

    @Test
    void testUpdateUserProfile_WhenProfileExists() {
        UUID userId = UUID.randomUUID();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserId(userId);
        userProfileRepository.save(entity);

        UpdateUserProfileRequest request = new UpdateUserProfileRequest(
                "Bob",
                "Builder",
                LocalDate.of(1988, 8, 8),
                "123 Construction Way",
                "Apt 4B",
                "London",
                "UK",
                "EC1A 1BB"
        );

        UserProfileDto updated = userService.updateUserProfile(userId, request);

        assertThat(updated).isNotNull();
        assertThat(updated.firstName()).isEqualTo("Bob");
        assertThat(updated.lastName()).isEqualTo("Builder");
        assertThat(updated.dateOfBirth()).isEqualTo(LocalDate.of(1988, 8, 8));
        assertThat(updated.addressLine1()).isEqualTo("123 Construction Way");
        assertThat(updated.addressLine2()).isEqualTo("Apt 4B");
        assertThat(updated.city()).isEqualTo("London");
        assertThat(updated.country()).isEqualTo("UK");
        assertThat(updated.postalCode()).isEqualTo("EC1A 1BB");
    }

    @Test
    void testUpdateUserProfile_WhenNotFound_ShouldThrowException() {
        UUID userId = UUID.randomUUID();
        UpdateUserProfileRequest request = new UpdateUserProfileRequest(
                "Bob", "Builder", LocalDate.now(), "123 Way", null, "City", "Country", "12345"
        );

        assertThatThrownBy(() -> userService.updateUserProfile(userId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User profile not found");
    }

    @Test
    void testUpdatePreferences_WhenProfileExists() {
        UUID userId = UUID.randomUUID();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserId(userId);
        userProfileRepository.save(entity);

        UpdatePreferencesRequest request = new UpdatePreferencesRequest("{\"theme\":\"dark\",\"currency\":\"USD\"}");
        UserProfileDto updated = userService.updatePreferences(userId, request);

        assertThat(updated).isNotNull();
        assertThat(updated.preferences()).isEqualTo("{\"theme\":\"dark\",\"currency\":\"USD\"}");
    }

    @Test
    void testUpdatePreferences_WhenNotFound_ShouldThrowException() {
        UUID userId = UUID.randomUUID();
        UpdatePreferencesRequest request = new UpdatePreferencesRequest("{\"theme\":\"dark\"}");

        assertThatThrownBy(() -> userService.updatePreferences(userId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User profile not found");
    }
}
