package com.repinsky.task_tracker_scheduler.converters;

import com.repinsky.task_tracker_scheduler.dto.TaskDto;
import com.repinsky.task_tracker_scheduler.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConverter {
    public TaskDto entityToDto(Task task) {
        return new TaskDto(
                task.getTitle(),
                task.getDescription()
        );
    }
}