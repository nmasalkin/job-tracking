package ru.vk.education.job;

import ru.vk.education.job.app.cli.CLI;
import ru.vk.education.job.app.service.JobService;
import ru.vk.education.job.app.service.MatchService;
import ru.vk.education.job.app.service.UserService;
import ru.vk.education.job.data.repository.JobRepository;
import ru.vk.education.job.data.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        JobRepository jobRepository = new JobRepository();
        UserRepository userRepository = new UserRepository();

        JobService jobService = new JobService(jobRepository);
        UserService userService = new UserService(userRepository);
        MatchService matchService = new MatchService(userRepository, jobRepository);

        CLI cli = new CLI(userService, jobService, matchService);
        cli.readCommands();
    }
}