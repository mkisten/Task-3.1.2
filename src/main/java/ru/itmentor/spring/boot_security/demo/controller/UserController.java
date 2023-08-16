package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String userInfo(Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "current_user";
    }

//    @GetMapping()
//    public String getAllUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "index";
//
//    }
//
//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "show";
//    }
//
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        return "new";
//    }
//
//    @PostMapping
//    public String create(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//    @GetMapping("/current_user")
//    public String showUserInfo(Model model, @PathVariable("username") String username){
//        model.addAttribute("user", userService.loadUserByUsername(username));
//        return "current_user";
//    }

//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
//        userService.updateUser(id, user);
//        return "redirect:/users";
//    }
//
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/users";
//    }
}
