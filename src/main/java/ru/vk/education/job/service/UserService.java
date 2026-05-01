package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.model.User;
import ru.vk.education.job.repository.UserRepository;

import java.util.List;

@Service
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
