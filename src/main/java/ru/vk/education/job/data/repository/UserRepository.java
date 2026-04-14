package ru.vk.education.job.data.repository;

import ru.vk.education.job.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public synchronized User add(User user) {
        if (findByName(user.getName()) != null) {
            return null;
        }
        users.add(user);
        return user;
    }

    public synchronized User findByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public synchronized List<User> getAll() {
        return new ArrayList<>(users);
    }
}
