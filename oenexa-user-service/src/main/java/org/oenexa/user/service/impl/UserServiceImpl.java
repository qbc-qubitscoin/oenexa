package org.oenexa.user.service.impl;

import org.oenexa.user.dto.request.UpdatePreferencesRequest;
import org.oenexa.user.dto.request.UpdateUserProfileRequest;
import org.oenexa.user.dto.response.UserProfileDto;
import org.oenexa.user.entity.UserProfileEntity;
import org.oenexa.user.repository.UserProfileRepository;
import org.oenexa.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(UUID userId) {
        UserProfileEntity entity = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        return mapToDto(entity);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(UUID userId, UpdateUserProfileRequest request) {
        UserProfileEntity entity = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setDateOfBirth(request.dateOfBirth());
        entity.setAddressLine1(request.addressLine1());
        entity.setAddressLine2(request.addressLine2());
        entity.setCity(request.city());
        entity.setCountry(request.country());
        entity.setPostalCode(request.postalCode());

        UserProfileEntity updated = userProfileRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public UserProfileDto updatePreferences(UUID userId, UpdatePreferencesRequest request) {
        UserProfileEntity entity = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        entity.setPreferences(request.preferences());

        UserProfileEntity updated = userProfileRepository.save(entity);
        return mapToDto(updated);
    }

    private UserProfileDto mapToDto(UserProfileEntity entity) {
        return new UserProfileDto(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth(),
                entity.getAddressLine1(),
                entity.getAddressLine2(),
                entity.getCity(),
                entity.getCountry(),
                entity.getPostalCode(),
                entity.getPreferences(),
                entity.getKycLevel()
        );
    }
}
