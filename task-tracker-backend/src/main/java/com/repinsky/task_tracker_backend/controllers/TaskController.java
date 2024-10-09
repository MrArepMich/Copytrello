package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.dto.TaskDto;
import com.repinsky.task_tracker_backend.dto.TaskRequest;
import com.repinsky.task_tracker_backend.services.TaskService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(new StringResponse(taskService.createTask(
                taskRequest.title(),
                taskRequest.description(),
                userService.getCurrentUserEmail()
        )));
    }

    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks(){
        return taskService.getAllUserTasks(userService.getCurrentUserEmail());
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteTask(@PathVariable String title){
        return ResponseEntity.ok(new StringResponse(taskService.deleteTask(title, userService.getCurrentUserEmail())));
    }
}
