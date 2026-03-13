package ru.vk.education.job.app.service.impl;

import ru.vk.education.job.app.service.UserService;
import ru.vk.education.job.data.model.User;
import ru.vk.education.job.data.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
