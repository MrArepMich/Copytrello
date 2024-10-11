package com.repinsky.task_tracker_backend.dto;

public record CreateTaskRequest(
        String title,
        String description
) {
}
