package org.oenexa.user.service;

import org.oenexa.user.dto.request.UpdatePreferencesRequest;
import org.oenexa.user.dto.request.UpdateUserProfileRequest;
import org.oenexa.user.dto.response.UserProfileDto;
import java.util.UUID;

public interface UserService {
    UserProfileDto getUserProfile(UUID userId);
    UserProfileDto updateUserProfile(UUID userId, UpdateUserProfileRequest request);
    UserProfileDto updatePreferences(UUID userId, UpdatePreferencesRequest request);
}
