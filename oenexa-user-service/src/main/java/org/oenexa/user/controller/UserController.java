package org.oenexa.user.controller;

import org.oenexa.security.common.util.SecurityUtils;
import org.oenexa.user.dto.request.UpdatePreferencesRequest;
import org.oenexa.user.dto.request.UpdateUserProfileRequest;
import org.oenexa.user.dto.response.UserProfileDto;
import org.oenexa.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserProfileDto profile = userService.getUserProfile(currentUserId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateMyProfile(@Validated @RequestBody UpdateUserProfileRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserProfileDto profile = userService.updateUserProfile(currentUserId, request);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/me/preferences")
    public ResponseEntity<UserProfileDto> updateMyPreferences(@Validated @RequestBody UpdatePreferencesRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserProfileDto profile = userService.updatePreferences(currentUserId, request);
        return ResponseEntity.ok(profile);
    }
}
