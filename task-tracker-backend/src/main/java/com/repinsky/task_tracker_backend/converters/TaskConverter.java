package com.repinsky.task_tracker_backend.converters;

import com.repinsky.task_tracker_backend.dto.TaskDto;
import com.repinsky.task_tracker_backend.models.Task;
import com.repinsky.task_tracker_backend.utils.TimestampConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConverter {

    public TaskDto entityToDto(Task task) {

        String formattedCreatedAt = TimestampConverterUtil.formatTimestamp(task.getCreatedAt());
        String formattedCompletedAt = TimestampConverterUtil.formatTimestamp(task.getCompletedAt());

        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                formattedCreatedAt,
                task.getStatus(),
                formattedCompletedAt
        );
    }
}
