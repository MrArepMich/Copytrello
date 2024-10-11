package com.repinsky.task_tracker_backend.converters;

import com.repinsky.task_tracker_backend.dto.TaskDto;
import com.repinsky.task_tracker_backend.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConverter {
    public TaskDto entityToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCompletedAt()
        );
    }
}
