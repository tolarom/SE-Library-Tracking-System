package project.library.demo.service;

import org.springframework.stereotype.Service;
import project.library.demo.entity.Role;
import project.library.demo.entity.ERole;
import project.library.demo.repo.RoleRepository;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        Objects.requireNonNull(id, "id");
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Role findByName(ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }
}