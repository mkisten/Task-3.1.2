package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.itmentor.spring.boot_security.demo.exception_handling.UserIncorrectData;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    private final UserService userService;

    @Autowired
    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id){
        User user = userService.getUserById(id);

        if(user == null){
            throw new NoSuchUserException("Пользователь с id = " + id + " не найден.");
        }
        return user;
    }
    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new NoSuchUserException("Пользователь с id = " + id + " не найден.");
        }

        userService.deleteUser(id);

        return new ResponseEntity<>("Пользователь с id = " + id + " удален.", HttpStatus.OK);
    }

}
