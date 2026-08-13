package org.oenexa.identity.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.oenexa.identity.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test & twilio")
@Slf4j
public class TwilioSmsService implements NotificationService {

    @Override
    public void sendEmailOtp(String to, String otp) {
        log.warn("TwilioSmsService cannot send emails. Ignoring email OTP request to: {}", to);
    }

    @Override
    public void sendSmsOtp(String to, String otp) {
        log.info("Simulating Twilio API call to send SMS OTP to {}...", to);
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Connect to Twilio API.
         * 
         * Procedure to implement in future:
         * 1. Add Twilio dependency to build.gradle.kts: 
         *    implementation("com.twilio.sdk:twilio:9.x.x")
         * 2. Add Twilio properties to application.yml (ACCOUNT_SID, AUTH_TOKEN, FROM_NUMBER).
         * 3. Inject these properties via @Value.
         * 4. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * Twilio.init(accountSid, authToken);
         * Message message = Message.creator(
         *         new com.twilio.type.PhoneNumber(to),
         *         new com.twilio.type.PhoneNumber(fromNumber),
         *         "Your Oenexa verification OTP is: " + otp
         * ).create();
         * 
         * log.info("Twilio SMS sent successfully with SID: {}", message.getSid());
         */
    }
}
