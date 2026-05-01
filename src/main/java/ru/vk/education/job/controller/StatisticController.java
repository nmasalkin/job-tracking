package ru.vk.education.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.User;
import ru.vk.education.job.service.StatisticService;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/jobs/by-experience")
    public List<Job> filterJobsByExperience(@RequestParam int n) {
        return statisticService.filterJobsByExperience(n);
    }

    @GetMapping("/users/by-matches")
    public List<User> filterUsersByMatches(@RequestParam int n) {
        return statisticService.filterUsersByMatches(n);
    }

    @GetMapping("/users/top-skills")
    public List<String> findTopUsersSkills(@RequestParam int n) {
        return statisticService.findTopUsersSkills(n);
    }
}
