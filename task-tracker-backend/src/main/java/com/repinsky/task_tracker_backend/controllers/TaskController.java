package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.constants.TaskStatus;
import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.dto.CreateTaskRequest;
import com.repinsky.task_tracker_backend.dto.UpdateTaskRequest;
import com.repinsky.task_tracker_backend.services.TaskService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getTaskByTitle(@PathVariable String title) {
        return ResponseEntity.ok(taskService.getTaskByTitle(title, userService.getCurrentUserEmail()));
    }

    @GetMapping()
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllUserTasks(userService.getCurrentUserEmail()));
    }

    @PostMapping()
    public ResponseEntity<?> createNewTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return ResponseEntity.ok(new StringResponse(taskService.createNewTask(
                createTaskRequest.title(),
                createTaskRequest.description(),
                userService.getCurrentUserEmail()
        )));
    }

    @PutMapping("/title/{title}")
    public ResponseEntity<?> updateTaskByTitle(@PathVariable String title, @RequestBody UpdateTaskRequest updateTaskRequest) {
        return ResponseEntity.ok(new StringResponse(taskService.updateTaskByTitle(
                title,
                userService.getCurrentUserEmail(),
                updateTaskRequest.title(),
                updateTaskRequest.description(),
                updateTaskRequest.status())));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAllTasks() {
        return ResponseEntity.ok(new StringResponse(taskService.deleteAllTasks(userService.getCurrentUserEmail())));
    }

    // tasks may not have titles, so user can pass an id and work with tasks
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id, userService.getCurrentUserEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(new StringResponse(taskService.deleteTaskById(id, userService.getCurrentUserEmail())));
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedTasks(){
        return ResponseEntity.ok(taskService.getTasksWithStatus(TaskStatus.COMPLETED, userService.getCurrentUserEmail()));
    }

    @GetMapping("/in-progress")
    public ResponseEntity<?> getInProgressTasks(){
        return ResponseEntity.ok(taskService.getTasksWithStatus(TaskStatus.IN_PROGRESS, userService.getCurrentUserEmail()));
    }
}
