package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
      public List<Role> getAllRoles() {
       return roleRepositories.findAll();
    }
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepositories.getRoleByRole(name);
    }

    @Transactional(readOnly = true)
    public Set<Role> getSetOfRoles(String[] roleNames) {
        return roleRepositories.getSetOfRolesByRoleIn(List.of(roleNames));
    }


    @Transactional(readOnly = true)
    public void add(Role role) {
        roleRepositories.save(role);
    }


    @Transactional(readOnly = true)
    public void edit(Role role) {
        roleRepositories.save(role);
    }


    @Transactional(readOnly = true)
    public Role getById(Long id) {
        return roleRepositories.getById(id);
    }

}
