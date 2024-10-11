package com.repinsky.task_tracker_backend.dto;

import java.sql.Timestamp;

public record TaskDto(
        Long id,
        String title,
        String description,
        String status,
        Timestamp completedAt
) {
}
