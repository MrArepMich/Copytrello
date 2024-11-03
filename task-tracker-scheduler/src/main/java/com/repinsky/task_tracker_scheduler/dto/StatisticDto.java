package com.repinsky.task_tracker_scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StatisticDto {
    String email;
    int countUnFinishedTasks;
    int countCompletedTasks;
    List<TaskDto> unFinishedTasks;
    List<TaskDto> completedTasks;
}
