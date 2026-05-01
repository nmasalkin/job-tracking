package ru.vk.education.job.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vk.education.job.model.BestJob;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.User;
import ru.vk.education.job.repository.BestJobRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BestJobSuggestionTask {

    private final BestJobRepository bestJobRepository;
    private final UserService userService;
    private final SuggestService suggestService;

    public BestJobSuggestionTask(BestJobRepository bestJobRepository, UserService userService, SuggestService suggestService) {
        this.bestJobRepository = bestJobRepository;
        this.userService = userService;
        this.suggestService = suggestService;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    private void findAndPrintBestJobs() {
        List<BestJob> bestJobs = new ArrayList<>();
        List<User> users = userService.getAll();
        for (User user : users) {
            List<Job> jobs = suggestService.suggest(user.getName());
            if (jobs != null && !jobs.isEmpty()) {
                bestJobs.add(new BestJob(user, jobs.get(0)));
            }
        }
        bestJobRepository.setBestJobs(bestJobs);
        bestJobs.forEach(System.out::println);
    }
}
