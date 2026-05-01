package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.Match;
import ru.vk.education.job.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestService {

    private final UserService userService;
    private final JobService jobService;

    public SuggestService(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }

    public List<Job> suggest(String name) {
        User user = userService.findByName(name);
        if (user == null) {
            return null;
        }
        List<Job> jobs = jobService.getAll();
        if (jobs == null) {
            return null;
        }
        List<Match> matches = new ArrayList<>();
        for (Job job : jobs) {
            matches.add(new Match(user, job));
        }
        int maxScore = matches.stream().mapToInt(Match::getScore).max().orElse(0);
        if (maxScore == 0) {
            return null;
        }
        List<Job> suggestedJobs = new ArrayList<>();
        while (suggestedJobs.size() < 2 && maxScore > 0) {
            for (Match match : matches) {
                if (match.getScore() == maxScore && suggestedJobs.size() < 2) {
                    suggestedJobs.add(match.getJob());
                }
            }
            maxScore--;
        }
        return suggestedJobs;
    }
}
