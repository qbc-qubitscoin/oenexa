package org.oenexa.identity.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.oenexa.identity.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod") // Use mock implementation except in production
@Slf4j
public class MockNotificationServiceImpl implements NotificationService {

    @Override
    public void sendEmailOtp(String email, String otp) {
        log.info("[MOCK] Sending Email OTP to {}: {}", email, otp);
    }

    @Override
    public void sendSmsOtp(String phoneNumber, String otp) {
        log.info("[MOCK] Sending SMS OTP to {}: {}", phoneNumber, otp);
    }
}
