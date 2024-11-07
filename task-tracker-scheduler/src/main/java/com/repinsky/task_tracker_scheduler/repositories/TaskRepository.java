package com.repinsky.task_tracker_scheduler.repositories;

import com.repinsky.task_tracker_scheduler.constants.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.repinsky.task_tracker_scheduler.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.owner.id = :ownerId")
    List<Task> findTasksByOwnerId(@Param("ownerId") Long ownerId);

    default List<Task> findTop5TasksByUserEmailAndStatus(String userEmail, TaskStatus status) {
        return findTop5ByUserEmailAndStatus(userEmail, status, PageRequest.of(0, 5));
    }

    @Query("select t from Task t where t.owner.email = :userEmail and t.status = :status")
    List<Task> findTop5ByUserEmailAndStatus(String userEmail,
                                            TaskStatus status,
                                            Pageable pageable);


    default List<Task> findTop5TasksByUserEmailAndCreatedAtBetweenAndStatus(String email,
                                                                            Timestamp start, Timestamp end,
                                                                            TaskStatus status){
        return findTop5ByUserEmailAndCreatedAtBetweenAndStatus(email, start, end,
                status, PageRequest.of(0, 5));
    }

    @Query("select t from Task t where t.owner.email = :email " +
            "AND t.completedAt between :start and :end " +
            "and t.status = :status order by t.createdAt desc")
    List<Task> findTop5ByUserEmailAndCreatedAtBetweenAndStatus(String email, Timestamp start, Timestamp end,
                                                               TaskStatus status, Pageable pageable);

}
