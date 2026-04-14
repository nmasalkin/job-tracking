package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.BestJob;
import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.model.User;
import ru.vk.education.job.data.repository.BestJobRepository;

import java.util.ArrayList;
import java.util.List;

public class BestJobSuggestionTask implements Runnable {

    private final BestJobRepository bestJobRepository;
    private final UserService userService;
    private final MatchService matchService;

    public BestJobSuggestionTask(BestJobRepository bestJobRepository, UserService userService, MatchService matchService) {
        this.bestJobRepository = bestJobRepository;
        this.userService = userService;
        this.matchService = matchService;
    }

    @Override
    public void run() {
        findBestJobs();
        printBestJobs();
    }

    private void findBestJobs() {
        List<BestJob> bestJobs = new ArrayList<>();
        List<User> users = userService.getAll();
        for (User user : users) {
            List<Job> jobs = matchService.suggest(user.getName());
            if (jobs != null && !jobs.isEmpty()) {
                bestJobs.add(new BestJob(user, jobs.get(0)));
            }
        }
        bestJobRepository.setBestJobs(bestJobs);
    }

    private void printBestJobs() {
        bestJobRepository.getBestJobs().forEach(System.out::println);
    }
}
