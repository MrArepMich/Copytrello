package com.repinsky.task_tracker_backend.repositories;

import com.repinsky.task_tracker_backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByTitle(String title);

    @Query("select r from Role r join r.users u where u.email = :userEmail")
    List<Role> findByUserEmail(@Param("userEmail") String userEmail);
}
