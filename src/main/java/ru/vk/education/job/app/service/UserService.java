package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.User;
import ru.vk.education.job.data.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(User user) {
        return userRepository.add(user);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
