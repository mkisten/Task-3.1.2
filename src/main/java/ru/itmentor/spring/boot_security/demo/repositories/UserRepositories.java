package ru.itmentor.spring.boot_security.demo.repositories;

import ru.itmentor.spring.boot_security.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String userName);
}
