package ru.vk.education.job;

import ru.vk.education.job.app.cli.CLI;
import ru.vk.education.job.app.service.*;
import ru.vk.education.job.data.repository.BestJobRepository;
import ru.vk.education.job.data.repository.JobRepository;
import ru.vk.education.job.data.repository.UserRepository;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("log.txt");

        JobRepository jobRepository = new JobRepository();
        UserRepository userRepository = new UserRepository();
        BestJobRepository bestJobRepository = new BestJobRepository();

        JobService jobService = new JobService(jobRepository);
        UserService userService = new UserService(userRepository);
        MatchService matchService = new MatchService(userService, jobService);
        FileService fileService = new FileService(path);
        StatisticService statisticService = new StatisticService(userService, jobService, matchService);
        CommandService commandService = new CommandService(userService, jobService, matchService, fileService, statisticService);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        BestJobSuggestionTask bestJobSuggestionTask = new BestJobSuggestionTask(bestJobRepository, userService, matchService);

        CLI cli = new CLI(commandService, fileService);
        cli.runCommandsFromFile();
        scheduledExecutorService.scheduleAtFixedRate(bestJobSuggestionTask, 0, 1, TimeUnit.MINUTES);
        cli.run();

        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}