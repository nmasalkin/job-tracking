package ru.vk.education.job.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.model.User;
import ru.vk.education.job.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }
}
