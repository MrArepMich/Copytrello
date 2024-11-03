package com.repinsky.task_tracker_scheduler.constants;

public enum TaskStatus {
    COMPLETED("COMPLETED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;
    TaskStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}