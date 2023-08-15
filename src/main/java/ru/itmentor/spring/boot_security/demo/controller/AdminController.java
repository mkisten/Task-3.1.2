package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String showAdminRootPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }
    @GetMapping("/users")
    public String showAllUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user";
    }

    @GetMapping("/new")
    public String showNewUserPage(@ModelAttribute("newUser") User newUser) {
        return "admin/newuser";
    }

    @PostMapping("/users")
    public String createNewUser(@ModelAttribute("newUser") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User editedUser, @PathVariable("id") Long id) {
        userService.updateUser(id, editedUser);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@ModelAttribute("user") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
