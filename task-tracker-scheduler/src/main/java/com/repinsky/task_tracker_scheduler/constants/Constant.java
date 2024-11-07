package com.repinsky.task_tracker_scheduler.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Constant {
    TODAY_START("todayStart"),
    TODAY_END("todayEnd");

    private final String value;

    public String getValue() {
        return value;
    }
}
