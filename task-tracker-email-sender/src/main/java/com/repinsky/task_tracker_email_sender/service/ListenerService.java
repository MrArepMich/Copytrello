package com.repinsky.task_tracker_email_sender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repinsky.task_tracker_email_sender.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerService {

    private final EmailService emailService;

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "task-tracker")
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
