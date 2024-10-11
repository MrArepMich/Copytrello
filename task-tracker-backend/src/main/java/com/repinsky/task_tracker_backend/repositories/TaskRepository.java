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
    Optional<List<Task>> findAllByUserEmail(String userEmail);

    @Query("select t from Task t where t.owner.email = :userEmail and t.title = :taskTitle")
    List<Task> findAllTasksByEmailAndTitle(String userEmail, String taskTitle);

    @Query("select t from Task t where t.owner.email = :userEmail and t.title = :taskTitle")
    Optional<Task> findTaskByTitleAndUserEmail(String taskTitle, String userEmail);

    @Query("select t from Task t where t.owner.email = :currentUserEmail and t.id = :id")
    Optional<Task> findTaskByUserEmailAndId(String currentUserEmail, Long id);

    @Query("select t from Task t where t.owner.email = :userEmail and t.status = :completedStatus")
    Optional<List<Task>> findTaskByStatusAndUserEmail(String completedStatus, String userEmail);
}
