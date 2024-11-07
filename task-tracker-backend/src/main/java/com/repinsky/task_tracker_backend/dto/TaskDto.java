package com.repinsky.task_tracker_backend.dto;

import com.repinsky.task_tracker_backend.constants.TaskStatus;

public record TaskDto(
        Long id,
        String title,
        String description,
        String createdAt,
        TaskStatus status,
        String completedAt
) {
}
