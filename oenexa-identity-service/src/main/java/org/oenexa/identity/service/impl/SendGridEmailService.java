package org.oenexa.identity.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.oenexa.identity.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test & sendgrid")
@Slf4j
public class SendGridEmailService implements NotificationService {

    @Override
    public void sendEmailOtp(String to, String otp) {
        log.info("Simulating SendGrid API call to send Email OTP to {}...", to);
        /*
         * TODO [FUTURE_IMPLEMENTATION]: Connect to SendGrid API.
         * 
         * Procedure to implement in future:
         * 1. Add SendGrid dependency to build.gradle.kts: 
         *    implementation("com.sendgrid:sendgrid-java:4.x.x")
         * 2. Add SendGrid properties to application.yml (SENDGRID_API_KEY, FROM_EMAIL).
         * 3. Inject these properties via @Value.
         * 4. Uncomment and use the code below.
         *
         * Implementation Code:
         * 
         * Email from = new Email(fromEmail);
         * String subject = "Your Oenexa Verification OTP";
         * Email sendTo = new Email(to);
         * Content content = new Content("text/plain", "Your OTP is: " + otp);
         * Mail mail = new Mail(from, subject, sendTo, content);
         * 
         * SendGrid sg = new SendGrid(sendgridApiKey);
         * Request request = new Request();
         * try {
         *     request.setMethod(Method.POST);
         *     request.setEndpoint("mail/send");
         *     request.setBody(mail.build());
         *     Response response = sg.api(request);
         *     log.info("SendGrid email sent. Status: {}", response.getStatusCode());
         * } catch (IOException ex) {
         *     log.error("Failed to send email via SendGrid", ex);
         *     throw new BusinessException("Failed to send email");
         * }
         */
    }

    @Override
    public void sendSmsOtp(String to, String otp) {
        log.warn("SendGridEmailService cannot send SMS. Ignoring SMS OTP request to: {}", to);
    }
}
