package com.repinsky.task_tracker_backend.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationProducer {
    private static final String TOPIC = "EMAIL_SENDING_TASKS";

    private static final String SUCCESSFUL_REGISTRATION_MESSAGE = """
            Hey, %s! Your registration was successful!.

            Best regards,
            Copytrello Support Team
            """;
    public static final String SUCCESS_REGISTRATION_SUBJECT = "Success registration";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendSuccessRegistration(String email) {
        Message message = new Message(email, SUCCESS_REGISTRATION_SUBJECT,
                SUCCESSFUL_REGISTRATION_MESSAGE.formatted(email));
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error while converting message to json", e);
        }
    }

    @Data
    @AllArgsConstructor
    private static class Message {
        private String recipient;
        private String subject;
        private String text;
    }
}
