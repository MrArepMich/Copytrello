package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.dto.CreateTaskRequest;
import com.repinsky.task_tracker_backend.dto.UpdateTaskRequest;
import com.repinsky.task_tracker_backend.services.TaskService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.repinsky.task_tracker_backend.constants.Constant.COMPLETED;
import static com.repinsky.task_tracker_backend.constants.Constant.IN_PROGRESS;

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

    @PatchMapping("/title/{title}")
    public ResponseEntity<?> patchTaskByTitle(@PathVariable String title, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(new StringResponse(taskService.patchTaskByTitle(title, userService.getCurrentUserEmail(), updates)));
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<?> deleteTaskByTitle(@PathVariable String title) {
        return ResponseEntity.ok(new StringResponse(taskService.deleteTaskByTitle(title, userService.getCurrentUserEmail())));
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(new StringResponse(taskService.updateTaskById(id, userService.getCurrentUserEmail(), updates)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> patchTaskById(@PathVariable Long id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        return ResponseEntity.ok(new StringResponse(taskService.patchTaskById(id,
                userService.getCurrentUserEmail(),
                updateTaskRequest.title(),
                updateTaskRequest.description(),
                updateTaskRequest.status())));
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedTasks(){
        return ResponseEntity.ok(taskService.getTasksWithStatus(COMPLETED.getValue(), userService.getCurrentUserEmail()));
    }

    @GetMapping("/in-progress")
    public ResponseEntity<?> getInProgressTasks(){
        return ResponseEntity.ok(taskService.getTasksWithStatus(IN_PROGRESS.getValue(), userService.getCurrentUserEmail()));
    }
}
