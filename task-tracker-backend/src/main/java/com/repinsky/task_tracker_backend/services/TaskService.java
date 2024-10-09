package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.converters.TaskConverter;
import com.repinsky.task_tracker_backend.dto.TaskDto;
import com.repinsky.task_tracker_backend.models.Task;
import com.repinsky.task_tracker_backend.models.User;
import com.repinsky.task_tracker_backend.repositories.TaskRepository;
import com.repinsky.task_tracker_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;

    public String createTask(String title, String description, String ownerEmail){
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + ownerEmail));

        Task task = Task.builder()
                .title(title)
                .description(description)
                .owner(owner)
                .status("in progress")
                .build();

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.saveNewTaskToDb(task);
        return "Task created successfully";
    }

    @Transactional
    protected void saveNewTaskToDb(Task task){
        taskRepository.save(task);
    }

    public List<TaskDto> getAllUserTasks(String userEmail) {
        return taskRepository.findAllByUserEmail(userEmail)
                .stream()
                .map(taskConverter::entityToDto)
                .toList();
    }

    public String deleteTask(String taskTitle, String userEmail) {
        Task task = taskRepository.findTaskByTitleAndUserEmail(taskTitle, userEmail);
        if (task != null) {
            taskRepository.delete(task);
            return "Task deleted successfully";
        } else {
            return "Task with title " + taskTitle + " does not exist";
        }
    }
}
