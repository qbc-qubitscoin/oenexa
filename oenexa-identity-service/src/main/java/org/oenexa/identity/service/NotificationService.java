package org.oenexa.identity.service;

public interface NotificationService {
    void sendEmailOtp(String email, String otp);
    void sendSmsOtp(String phoneNumber, String otp);
}
