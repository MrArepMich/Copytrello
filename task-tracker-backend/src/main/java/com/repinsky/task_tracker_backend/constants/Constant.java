package com.repinsky.task_tracker_backend.constants;

public enum Constant {
    ROLES("roles"),
    BEARER("Bearer"),
    COMPLETED("completed"),
    IN_PROGRESS("in progress");

    private final String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
