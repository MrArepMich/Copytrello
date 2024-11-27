package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.constants.TaskStatus;
import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.dto.CreateTaskRequest;
import com.repinsky.task_tracker_backend.dto.UpdateTaskRequest;
import com.repinsky.task_tracker_backend.services.TaskService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getTaskByTitle(@PathVariable String title) {
        log.info("Received get request to get task with title '{}' for user email: {}", title, userService.getCurrentUserEmail());
        return ResponseEntity.ok(taskService.getTaskByTitle(title, userService.getCurrentUserEmail()));
    }

    @GetMapping()
    public ResponseEntity<?> getAllTasks() {
        log.info("Received request to get all tasks for user email: {}", userService.getCurrentUserEmail());
        return ResponseEntity.ok(taskService.getAllUserTasks(userService.getCurrentUserEmail()));
    }

    @PostMapping()
    public ResponseEntity<?> createNewTask(@RequestBody CreateTaskRequest createTaskRequest) {
        log.info("Received request to create task with title '{}' and description '{}' for user email: {}", createTaskRequest.title(), createTaskRequest.description(), userService.getCurrentUserEmail());
        return ResponseEntity.ok(new StringResponse(taskService.createNewTask(
                createTaskRequest.title(),
                createTaskRequest.description(),
                userService.getCurrentUserEmail()
        )));
    }

    @PutMapping("/title/{title}")
    public ResponseEntity<?> updateTaskByTitle(@PathVariable String title, @RequestBody UpdateTaskRequest updateTaskRequest) {
        log.info("Received request to update task with title '{}' and description '{}' and status '{}' for user email: {}", updateTaskRequest.title(), updateTaskRequest.description(), updateTaskRequest.status(), userService.getCurrentUserEmail());
        return ResponseEntity.ok(new StringResponse(taskService.updateTaskByTitle(
                title,
                userService.getCurrentUserEmail(),
                updateTaskRequest.title(),
                updateTaskRequest.description(),
                updateTaskRequest.status())));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAllTasks() {
        log.info("Received request to delete all tasks for user email: {}", userService.getCurrentUserEmail());
        return ResponseEntity.ok(new StringResponse(taskService.deleteAllTasks(userService.getCurrentUserEmail())));
    }

    // tasks may not have titles, so user can pass an id and work with tasks
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        log.info("Received request to get task by id '{}' for user email: {}", id, userService.getCurrentUserEmail());
        return ResponseEntity.ok(taskService.getTaskById(id, userService.getCurrentUserEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        log.info("Received request to delete task by id '{}' for user email: {}", id, userService.getCurrentUserEmail());
        return ResponseEntity.ok(new StringResponse(taskService.deleteTaskById(id, userService.getCurrentUserEmail())));
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedTasks(){
        log.info("Received request to get all completed tasks for user email: {}", userService.getCurrentUserEmail());
        return ResponseEntity.ok(taskService.getTasksWithStatus(TaskStatus.COMPLETED, userService.getCurrentUserEmail()));
    }

    @GetMapping("/in-progress")
    public ResponseEntity<?> getInProgressTasks(){
        log.info("Received request to get all in-progress tasks for user email: {}", userService.getCurrentUserEmail());
        return ResponseEntity.ok(taskService.getTasksWithStatus(TaskStatus.IN_PROGRESS, userService.getCurrentUserEmail()));
    }
}
