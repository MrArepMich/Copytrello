package com.repinsky.task_tracker_backend.dto;

public record UpdateTaskRequest(
        String title,
        String description,
        String status
) {
}
