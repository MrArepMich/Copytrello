package com.repinsky.task_tracker_email_sender.dto;

public record MessageDto(
        String recipient,
        String subject,
        String text
) {
}
