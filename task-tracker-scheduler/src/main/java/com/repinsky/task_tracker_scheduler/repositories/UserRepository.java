package com.repinsky.task_tracker_scheduler.repositories;

import com.repinsky.task_tracker_scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
