package org.oenexa.identity.service;

import org.junit.jupiter.api.Test;
import org.oenexa.identity.service.impl.MockNotificationServiceImpl;
import org.oenexa.identity.service.impl.SendGridEmailService;
import org.oenexa.identity.service.impl.TwilioSmsService;

public class NotificationServicesTest {

    @Test
    void testMockNotificationService() {
        MockNotificationServiceImpl mockService = new MockNotificationServiceImpl();
        mockService.sendEmailOtp("test@example.com", "123456");
        mockService.sendSmsOtp("+1234567890", "123456");
    }

    @Test
    void testSendGridEmailService() {
        SendGridEmailService sendGridService = new SendGridEmailService();
        sendGridService.sendEmailOtp("test@example.com", "123456");
        sendGridService.sendSmsOtp("+1234567890", "123456");
    }

    @Test
    void testTwilioSmsService() {
        TwilioSmsService twilioService = new TwilioSmsService();
        twilioService.sendEmailOtp("test@example.com", "123456");
        twilioService.sendSmsOtp("+1234567890", "123456");
    }
}
