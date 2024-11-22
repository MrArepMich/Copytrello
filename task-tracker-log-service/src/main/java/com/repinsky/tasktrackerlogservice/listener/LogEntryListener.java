package com.repinsky.tasktrackerlogservice.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class LogEntryListener {

    @KafkaListener(topics = "LOG_SENDING_TASKS", groupId = "task-tracker")
    public void listen(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MessageDto dto = objectMapper.readValue(message, MessageDto.class);
            emailService.sendEmail(dto.recipient(), dto.subject(), dto.text());
        } catch (JsonProcessingException e) {
            log.error("Error while converting message to json", e);
        }
    }
}
