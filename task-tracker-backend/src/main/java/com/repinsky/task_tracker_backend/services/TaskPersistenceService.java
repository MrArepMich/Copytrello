package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.models.Task;
import com.repinsky.task_tracker_backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskPersistenceService {
    private final TaskRepository taskRepository;

    @Transactional
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Transactional
    public void deleteTasks(List<Task> tasks) {
        taskRepository.deleteAll(tasks);
    }
}
