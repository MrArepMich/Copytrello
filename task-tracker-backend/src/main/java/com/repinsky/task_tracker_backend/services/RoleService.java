package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.models.Role;
import com.repinsky.task_tracker_backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getManagerRole() {
        return roleRepository.findByTitle("ROLE_USER").orElseThrow();
    }

    public Role getRoleByName(String role) {
        return roleRepository.findByTitle(role).orElseThrow();
    }

    public List<String> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getTitle)
                .toList();
    }

    public List<Role> findForUserName(String username) {
        return roleRepository.findByUserEmail(username);
    }
}
