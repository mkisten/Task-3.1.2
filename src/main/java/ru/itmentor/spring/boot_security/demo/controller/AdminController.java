package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String homeAdmin() {
        //возвращаю страницу админа
        return "redirect:/admin/users";
    }
    @GetMapping("users")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        //домашняя страница
        return "all_users";
    }
    @GetMapping(value = "users/add")
    public String newUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        //возвращаю страницу с добавлением юзеров
        return "add_user";
    }
    @PostMapping(value = "users/add")
    public String createNewUser(@ModelAttribute("user") User user
            , @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.saveUser(user);
        //возвращаю страницу с добавлением юзеров
        return "redirect:/admin/users";
    }
    @GetMapping("users/{id}/edit")
    public String editUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        //ретёрн возвращает страницу с редактированием юзеров + исправлено замечание с задания 2.3.1 заменить примитивный тип на обёртку
        return "edit_user";
    }

    @PatchMapping("users/{id}/edit")
    public String update(@ModelAttribute("user") User user
            , @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("users/{id}/delete")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        // ретёрн возвращает страницу с удалением пользователя +
        // исправлено замечание с задания 2.3.1 заменить примитивный тип на обёртку +
        // испрвленно замечание с 2.3.1 исплользовать нужно аннотацию PathVariable
        return "redirect:/admin/users";
    }
    @GetMapping("users/{id}")
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        // ретёрн возвращает страницу с указанием пользователей +
        return "user_info_by_id";
    }

}
