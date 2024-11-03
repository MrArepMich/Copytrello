package com.repinsky.task_tracker_backend.dto;

import com.repinsky.task_tracker_backend.constants.TaskStatus;

public record UpdateTaskRequest(
        String title,
        String description,
        String status
) {
}
