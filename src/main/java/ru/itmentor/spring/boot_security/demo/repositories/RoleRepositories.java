package ru.itmentor.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.model.Role;

public interface RoleRepositories extends JpaRepository<Role, Integer> {
}
