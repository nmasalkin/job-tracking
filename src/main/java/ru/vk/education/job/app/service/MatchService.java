package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.model.Match;
import ru.vk.education.job.data.model.User;
import ru.vk.education.job.data.repository.JobRepository;
import ru.vk.education.job.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class MatchService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public MatchService(UserRepository userRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public List<Job> suggest(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            return null;
        }
        List<Job> jobs = jobRepository.getAll();
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
