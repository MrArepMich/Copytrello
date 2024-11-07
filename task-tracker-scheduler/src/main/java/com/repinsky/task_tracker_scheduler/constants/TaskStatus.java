package com.repinsky.task_tracker_scheduler.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TaskStatus {
    COMPLETED("COMPLETED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;

    public String getValue() {
        return value;
    }
}