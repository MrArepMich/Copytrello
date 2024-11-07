package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.constants.TaskStatus;
import com.repinsky.task_tracker_backend.converters.TaskConverter;
import com.repinsky.task_tracker_backend.dto.TaskDto;
import com.repinsky.task_tracker_backend.exceptions.BadRequestException;
import com.repinsky.task_tracker_backend.exceptions.TaskNotFoundException;
import com.repinsky.task_tracker_backend.exceptions.UserNotFoundException;
import com.repinsky.task_tracker_backend.models.Task;
import com.repinsky.task_tracker_backend.models.User;
import com.repinsky.task_tracker_backend.repositories.TaskRepository;
import com.repinsky.task_tracker_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;

    public String createNewTask(String title, String description, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email '" + ownerEmail + "' not found"));

        if (taskRepository.findTaskByTitleAndUserEmail(title, ownerEmail).isPresent()) {
            throw new BadRequestException("Task with title '" + title + "' already exists");
        }

        Task newTask = Task.builder()
                .title(title)
                .description(description)
                .owner(owner)
                .status(TaskStatus.IN_PROGRESS)
                .build();

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.saveTaskToDb(newTask);

        return "Task created successfully";
    }

    public List<TaskDto> getAllUserTasks(String userEmail) {
        return taskRepository.findAllByUserEmail(userEmail).orElseThrow(
                        () -> new TaskNotFoundException("Tasks for user '" + userEmail + "' not found")
                )
                .stream()
                .map(taskConverter::entityToDto)
                .toList();
    }

    public String deleteAllTasks(String currentUserEmail) {
        List<Task> tasks = taskRepository.findAllByUserEmail(currentUserEmail).orElseThrow(
                () -> new TaskNotFoundException("Tasks for user '" + currentUserEmail + "' not found")
        );

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.deleteAllUserTasks(tasks);

        return "All tasks deleted successfully";
    }

    public TaskDto getTaskById(Long id, String currentUserEmail) {
        /* The user has access only to their own tasks.
         * The task with the current ID may exist, but it belongs to another user. */
        Task task = taskRepository.findTaskByUserEmailAndId(currentUserEmail, id).orElseThrow(
                () -> new TaskNotFoundException("Task with id '" + id + "' for user '" + currentUserEmail + "' does not exist")
        );

        return taskConverter.entityToDto(task);
    }

    public TaskDto getTaskByTitle(String title, String currentUserEmail) {
        Task task = taskRepository.findTaskByTitleAndUserEmail(title, currentUserEmail).orElseThrow(
                () -> new TaskNotFoundException("Task with title '" + title + "' does not exist")
        );

        return taskConverter.entityToDto(task);
    }

    public String deleteTaskById(Long id, String currentUserEmail) {
        Task task = taskRepository.findTaskByUserEmailAndId(currentUserEmail, id).orElseThrow(
                () -> new TaskNotFoundException("Task with id '" + id + "' for user '" + currentUserEmail + "' does not exist")
        );

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.deleteTaskFromDb(task);

        return "Task deleted successfully";
    }

    public List<TaskDto> getTasksWithStatus(TaskStatus status, String currentUserEmail) {
        List<Task> tasks = taskRepository.findTaskByUserEmailAndStatus(currentUserEmail, status);

        return tasks.stream()
                .map(taskConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public String updateTaskByTitle(String title, String currentUserEmail, String newTitle, String newDescription, String newStatus) {
        Task task = taskRepository.findTaskByTitleAndUserEmail(title, currentUserEmail).orElseThrow(
                () -> new TaskNotFoundException("Task with title '" + title + "' does not exist")
        );

        return updateTaskWithNewValues(currentUserEmail, newTitle, newDescription, newStatus, task);
    }

    private String updateTaskWithNewValues(String userEmail, String newTitle, String newDescription, String newStatus, Task task) {
        if (newTitle == null || newStatus == null) {
            throw new TaskNotFoundException("Title and status for new task cannot be empty");
        }

        List<Task> tasks = taskRepository.findAllTasksByEmailAndTitle(userEmail, newTitle);

        if (!tasks.isEmpty() && !tasks.contains(task)) {
            throw new BadRequestException("Task with title '" + newTitle + "' already exists");
        }

        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: " + newStatus);
        }

        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setStatus(taskStatus);

        if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        } else {
            task.setCompletedAt(null);
        }

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.saveTaskToDb(task);

        return "Task updated successfully";
    }

    @Transactional
    protected void deleteTaskFromDb(Task task) {
        taskRepository.delete(task);
    }

    @Transactional
    protected void deleteAllUserTasks(List<Task> tasks) {
        taskRepository.deleteAll(tasks);
    }

    @Transactional
    protected void saveTaskToDb(Task task) {
        taskRepository.save(task);
    }
}
