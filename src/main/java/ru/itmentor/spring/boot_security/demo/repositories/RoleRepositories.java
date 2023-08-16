package ru.itmentor.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepositories extends JpaRepository<Role, Integer> {
    Optional<Role> findUserByRole(String role);
    Set<Role> getSetOfRolesByRoleIn(Collection<String> roleNames);
   // Set<Role> getSetOfRolesByIn(Collection<String> roleNames);
    Role getRoleByRole(String name);
    Role getById(Long ID);

}
