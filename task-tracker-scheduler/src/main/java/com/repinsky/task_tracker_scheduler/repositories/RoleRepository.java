package com.repinsky.task_tracker_scheduler.repositories;

import com.repinsky.task_tracker_scheduler.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r join r.users u where u.id = :userId")
    Collection<Role> findRolesByUserId(@Param("userId") Long userId);
}
