package com.repinsky.task_tracker_scheduler.produser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repinsky.task_tracker_scheduler.dto.StatisticDto;
import com.repinsky.task_tracker_scheduler.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SummaryProducer {
    private static final String TOPIC = "EMAIL_SENDING_TASKS";

    private static final String JSON_CONVERSION_ERROR = "Error converting message to JSON";
    private static final String UNFINISHED_TASKS_MESSAGE = """
            Dear %s,

            You have %d unfinished tasks:
            %s

            Best regards,
            Copytrello Support Team
            """;
    private static final String FINISHED_TASKS_MESSAGE = """
            Dear %s,

            You have %d completed tasks:
            %s

            Best regards,
            Copytrello Support Team
            """;
    private static final String ALL_TASKS_MESSAGE = """
            Dear %s,

            You have %d unfinished tasks and %d completed tasks:
            Unfinished tasks: %s
            Completed tasks: %s

            Best regards,
            Copytrello Support Team
            """;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public SummaryProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    private void send(Message message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error(JSON_CONVERSION_ERROR, e);
        }
    }

    public void sendUnfinishedTasks(StatisticDto dto) {
        String formatted = String.format(UNFINISHED_TASKS_MESSAGE, dto.getEmail(),
                dto.getCountUnFinishedTasks(), formatTasks(dto.getUnFinishedTasks()));
        send(new Message(dto.getEmail(), "In progress tasks", formatted));
    }

    public void sendCompletedTasks(StatisticDto dto) {
        String formatted = String.format(FINISHED_TASKS_MESSAGE, dto.getEmail(),
                dto.getCountCompletedTasks(), formatTasks(dto.getCompletedTasks()));
        send(new Message(dto.getEmail(), "Completed tasks", formatted));
    }

    public void sendAllTasks(StatisticDto dto) {
        String allTaskMessageFormatted = String.format(ALL_TASKS_MESSAGE, dto.getEmail(), dto.getCountUnFinishedTasks(),
                dto.getCountCompletedTasks(),
                formatTasks(dto.getUnFinishedTasks()), formatTasks(dto.getCompletedTasks()));
        send(new Message(dto.getEmail(), "All tasks statistic", allTaskMessageFormatted));
    }

    private String formatTasks(List<TaskDto> tasks) {
        StringBuilder formattedTasks = new StringBuilder();
        for (TaskDto task : tasks) {
            formattedTasks.append("\n - ").append(task.title());
            if (task.description() != null) {
                formattedTasks.append(": ").append(task.description());
            }
        }
        return formattedTasks.toString();
    }

    @Data
    @AllArgsConstructor
    private static class Message {
        private String recipient;
        private String subject;
        private String text;
    }
}
