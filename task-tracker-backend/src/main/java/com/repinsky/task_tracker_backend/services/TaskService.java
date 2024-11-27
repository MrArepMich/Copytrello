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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
            log.error("User '{}' tried to create a task with an existing title '{}'", ownerEmail, title);
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

        log.info("Task with title '{}' and description '{}' for user '{}' created successfully", title, description, ownerEmail);
        return "Task created successfully";
    }

    public List<TaskDto> getAllUserTasks(String userEmail) {
        log.info("Tasks for user '{}' found", userEmail);
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

        log.info("Tasks for user '{}' deleted successfully", currentUserEmail);
        return "All tasks deleted successfully";
    }

    public TaskDto getTaskById(Long id, String currentUserEmail) {
        /* The user has access only to their own tasks.
         * The task with the current ID may exist, but it belongs to another user. */
        Task task = taskRepository.findTaskByUserEmailAndId(currentUserEmail, id).orElseThrow(
                () -> new TaskNotFoundException("Task with id '" + id + "' for user '" + currentUserEmail + "' does not exist")
        );

        log.info("Task with id '{}' for user '{}' found", id, currentUserEmail);
        return taskConverter.entityToDto(task);
    }

    public TaskDto getTaskByTitle(String title, String currentUserEmail) {
        Task task = taskRepository.findTaskByTitleAndUserEmail(title, currentUserEmail).orElseThrow(
                () -> new TaskNotFoundException("Task with title '" + title + "' does not exist")
        );

        log.info("Task with title '{}' for user '{}' found", title, currentUserEmail);
        return taskConverter.entityToDto(task);
    }

    public String deleteTaskById(Long id, String currentUserEmail) {
        Task task = taskRepository.findTaskByUserEmailAndId(currentUserEmail, id).orElseThrow(
                () -> new TaskNotFoundException("Task with id '" + id + "' for user '" + currentUserEmail + "' does not exist")
        );

        TaskService proxy = applicationContext.getBean(TaskService.class);
        proxy.deleteTaskFromDb(task);

        log.info("Task with id '{}' for email '{}' deleted successfully", id, currentUserEmail);
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
            log.error("Title and status for new task cannot be empty");
            throw new TaskNotFoundException("Title and status for new task cannot be empty");
        }

        List<Task> tasks = taskRepository.findAllTasksByEmailAndTitle(userEmail, newTitle);

        if (!tasks.isEmpty() && !tasks.contains(task)) {
            log.error("User '{}' tried to update a task with an already existing title '{}'", userEmail, newTitle);
            throw new BadRequestException("Task with title '" + newTitle + "' already exists");
        }

        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid task status '{}'", newStatus);
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

        log.info("Task with title '{}' for user '{}' updated successfully", newTitle, userEmail);
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
