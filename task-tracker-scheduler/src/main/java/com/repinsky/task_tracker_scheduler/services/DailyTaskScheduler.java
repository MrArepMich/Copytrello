package com.repinsky.task_tracker_scheduler.services;

import com.repinsky.task_tracker_scheduler.constants.TaskStatus;
import com.repinsky.task_tracker_scheduler.converters.TaskConverter;
import com.repinsky.task_tracker_scheduler.dto.StatisticDto;
import com.repinsky.task_tracker_scheduler.dto.TaskDto;
import com.repinsky.task_tracker_scheduler.models.Task;
import com.repinsky.task_tracker_scheduler.models.User;
import com.repinsky.task_tracker_scheduler.produser.SummaryProducer;
import com.repinsky.task_tracker_scheduler.repositories.RoleRepository;
import com.repinsky.task_tracker_scheduler.repositories.TaskRepository;
import com.repinsky.task_tracker_scheduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DailyTaskScheduler {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final SummaryProducer summaryProducer;
    private final RoleRepository roleRepository;

    @Scheduled(cron = "${scheduling.cron}")
    public void sendTaskStatistic(){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setRoles(roleRepository.findRolesByUserId(user.getId()));
            user.setTasks(taskRepository.findTasksByOwnerId(user.getId()));

            List<Task> completedTasks = taskRepository.findTop5TasksByUserEmailAndStatus(user.getEmail(), TaskStatus.COMPLETED);
            List<Task> inProgressTasks = taskRepository.findTop5TasksByUserEmailAndStatus(user.getEmail(), TaskStatus.IN_PROGRESS);

            if (completedTasks.isEmpty() && inProgressTasks.isEmpty()) {
                continue;
            }

            if (!completedTasks.isEmpty() && !inProgressTasks.isEmpty()) {
                summaryProducer.sendAllTasks(fillStatisticDto(user, completedTasks, inProgressTasks));
            } else if (!completedTasks.isEmpty()) {
                summaryProducer.sendCompletedTasks(fillStatisticDto(user, completedTasks, inProgressTasks));
            } else {
                summaryProducer.sendUnfinishedTasks(fillStatisticDto(user, completedTasks, inProgressTasks));
            }
        }
    }

    private StatisticDto fillStatisticDto(User user, List<Task> completedTasks, List<Task> inProgressTasks) {
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setEmail(user.getEmail());

        List<TaskDto> completedTaskDtos = convertTasksToDto(completedTasks);
        List<TaskDto> unFinishedTaskDtos = convertTasksToDto(inProgressTasks);

        statisticDto.setCountCompletedTasks(completedTasks.size());
        statisticDto.setCountUnFinishedTasks(inProgressTasks.size());
        statisticDto.setCompletedTasks(completedTaskDtos);
        statisticDto.setUnFinishedTasks(unFinishedTaskDtos);

        return statisticDto;
    }

    private List<TaskDto> convertTasksToDto(List<Task> tasks) {
        return tasks.stream()
                .map(taskConverter::entityToDto)
                .toList();
    }
}
