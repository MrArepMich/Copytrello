package com.repinsky.task_tracker_backend.dto;

import com.repinsky.task_tracker_backend.constants.TaskStatus;

import java.sql.Timestamp;

public record TaskDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Timestamp completedAt
) {
}
