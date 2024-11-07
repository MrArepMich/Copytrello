package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.models.Role;
import com.repinsky.task_tracker_backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getManagerRole() {
        return roleRepository.findByTitle("ROLE_USER").orElseThrow();
    }
}
