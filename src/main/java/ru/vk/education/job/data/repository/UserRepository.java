package ru.vk.education.job.data.repository;

import ru.vk.education.job.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public User add(User user) {
        if (findByName(user.getName()) != null) {
            return null;
        }
        users.add(user);
        return user;
    }

    public User findByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAll() {
        return users;
    }
}
