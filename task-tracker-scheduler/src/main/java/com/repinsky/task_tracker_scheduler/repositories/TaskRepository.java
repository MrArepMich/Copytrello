package com.repinsky.task_tracker_scheduler.repositories;

import com.repinsky.task_tracker_scheduler.constants.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.repinsky.task_tracker_scheduler.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.owner.email = :userEmail AND t.status = :status")
    List<Task> findTop5ByUserEmailAndStatus(@Param("userEmail") String userEmail,
                                            @Param("status") TaskStatus status,
                                            Pageable pageable);

    default List<Task> findTop5TasksByUserEmailAndStatus(String userEmail, TaskStatus status) {
        return findTop5ByUserEmailAndStatus(userEmail, status, PageRequest.of(0, 5));
    }

    @Query("select t from Task t where t.owner.id = :ownerId")
    List<Task> findTasksByOwnerId(@Param("ownerId") Long ownerId);
}
