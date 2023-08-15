package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;

import java.util.Set;
@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepositories roleRepositories;

    @Autowired
    public RoleService(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {

        return (Set)roleRepositories.findAll();
    }
}
