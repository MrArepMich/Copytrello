package com.repinsky.task_tracker_backend.repositories;

import com.repinsky.task_tracker_backend.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.owner.email = :userEmail")
    List<Task> findAllByUserEmail(String userEmail);

    void deleteByTitle(String taskName);

    @Query("select t from Task t where t.owner.email = :userEmail and t.title = :taskTitle ")
    Task findTaskByTitleAndUserEmail(String taskTitle, String userEmail);
}
