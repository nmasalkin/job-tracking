package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.User;

import java.util.List;

public interface UserService {

    User add(User user);

    User findByName(String name);

    List<User> getAll();
}
