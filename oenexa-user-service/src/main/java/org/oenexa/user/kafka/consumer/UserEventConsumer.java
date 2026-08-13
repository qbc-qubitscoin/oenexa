package org.oenexa.user.kafka.consumer;

import org.oenexa.user.entity.KycLevel;
import org.oenexa.user.entity.UserProfileEntity;
import org.oenexa.user.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserEventConsumer.class);
    
    private final UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper;

    public UserEventConsumer(UserProfileRepository userProfileRepository, ObjectMapper objectMapper) {
        this.userProfileRepository = userProfileRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "identity.user.registered", groupId = "oenexa-user-service-group")
    public void handleUserRegistered(String eventPayload) {
        try {
            UserRegisteredEvent event = objectMapper.readValue(eventPayload, UserRegisteredEvent.class);
            log.info("Received UserRegisteredEvent for user: {}", event.userId());
            if (!userProfileRepository.existsById(event.userId())) {
                UserProfileEntity profile = new UserProfileEntity();
                profile.setUserId(event.userId());
                profile.setKycLevel(KycLevel.NONE);
                userProfileRepository.save(profile);
                log.info("Created default user profile for user: {}", event.userId());
            }
        } catch (Exception e) {
            log.error("Failed to parse event", e);
        }
    }

    @KafkaListener(topics = "kyc.status.updated", groupId = "oenexa-user-service-group")
    public void handleKycStatusUpdated(String eventPayload) {
        try {
            KycStatusUpdatedEvent event = objectMapper.readValue(eventPayload, KycStatusUpdatedEvent.class);
            log.info("Received KycStatusUpdatedEvent for user: {} with status: {}", event.userId(), event.status());
            userProfileRepository.findById(event.userId()).ifPresent(profile -> {
                if ("VERIFIED".equals(event.status())) {
                    profile.setKycLevel(KycLevel.BASIC);
                } else if ("REJECTED".equals(event.status())) {
                    profile.setKycLevel(KycLevel.NONE);
                }
                userProfileRepository.save(profile);
                log.info("Updated KYC level for user: {}", event.userId());
            });
        } catch (Exception e) {
            log.error("Failed to parse event", e);
        }
    }
}
