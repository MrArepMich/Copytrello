package com.repinsky.task_tracker_backend.dto;

public record TaskRequest(
        String title,
        String description
) {
}
