package com.repinsky.task_tracker_scheduler.utils;

import com.repinsky.task_tracker_scheduler.dto.TaskDto;

import java.util.List;

public class FormatTaskUtil {

    public static String formatTasks(List<TaskDto> tasks) {
        StringBuilder formattedTasks = new StringBuilder();
        for (TaskDto task : tasks) {
            formattedTasks.append("\n - ").append(task.title());
            if (task.description() != null) {
                formattedTasks.append(": ").append(task.description());
            }
        }
        return formattedTasks.toString();
    }
}
