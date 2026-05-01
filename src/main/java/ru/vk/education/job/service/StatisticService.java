package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class StatisticService {

    private final UserService userService;
    private final JobService jobService;
    private final SuggestService suggestService;

    public StatisticService(UserService userService, JobService jobService, SuggestService suggestService) {
        this.userService = userService;
        this.jobService = jobService;
        this.suggestService = suggestService;
    }

    public List<Job> filterJobsByExperience(int n) {
        return jobService.getAll().stream()
                .filter(job -> job.getExperience() >= n)
                .sorted(Comparator.comparing(Job::getTitle)).toList();
    }

    public List<User> filterUsersByMatches(int n) {
        return userService.getAll().stream()
                .filter(user -> suggestService.suggest(user.getName()) != null
                                && suggestService.suggest(user.getName()).size() >= n).toList();
    }

    public List<String> findTopUsersSkills(int n){
        HashMap<String, Integer> skills = new HashMap<>();
        userService.getAll().forEach(user -> user.getSkills()
                .forEach(skill -> skills.put(skill, skills.get(skill) != null ? skills.get(skill) + 1 : 1)));
        int maxValue = skills.values().stream().max(Integer::compareTo).get();
        List<String> top = new ArrayList<>();
        while (maxValue > 0) {
            int finalMaxValue = maxValue;
            top.addAll(skills.keySet().stream()
                    .filter(key -> skills.get(key)
                            .equals(finalMaxValue)).sorted().toList());
            maxValue--;
        }
        return top.subList(0, n);
    }
}
