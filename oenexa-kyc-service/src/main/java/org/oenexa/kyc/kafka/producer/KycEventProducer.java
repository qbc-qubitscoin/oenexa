package org.oenexa.kyc.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KycEventProducer {

    private static final String TOPIC_KYC_STATUS_UPDATED = "kyc.status.updated";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KycEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishKycStatusUpdated(KycStatusUpdatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC_KYC_STATUS_UPDATED, event.userId().toString(), payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize KYC event", e);
        }
    }
}
