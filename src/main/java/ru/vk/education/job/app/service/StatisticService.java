package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StatisticService {

    private final UserService userService;
    private final JobService jobService;
    private final MatchService matchService;

    public StatisticService(UserService userService, JobService jobService, MatchService matchService) {
        this.userService = userService;
        this.jobService = jobService;
        this.matchService = matchService;
    }

    public List<Job> filterJobsByExperience(int n) {
        return jobService.getAll().stream()
                .filter(job -> job.getExperience() >= n)
                .sorted(Comparator.comparing(Job::getTitle)).toList();
    }

    public List<User> filterUsersByMatches(int n) {
        return userService.getAll().stream()
                .filter(user -> matchService.suggest(user.getName()) != null
                                && matchService.suggest(user.getName()).size() >= n).toList();
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
