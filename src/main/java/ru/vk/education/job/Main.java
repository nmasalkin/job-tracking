package ru.vk.education.job;

import ru.vk.education.job.app.cli.CLI;
import ru.vk.education.job.app.service.impl.JobServiceImpl;
import ru.vk.education.job.app.service.impl.MatchServiceImpl;
import ru.vk.education.job.app.service.impl.UserServiceImpl;
import ru.vk.education.job.data.repository.JobRepository;
import ru.vk.education.job.data.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        JobRepository jobRepository = new JobRepository();
        UserRepository userRepository = new UserRepository();

        JobService jobService = new JobServiceImpl(jobRepository);
        UserService userService = new UserServiceImpl(userRepository);
        MatchService matchService = new MatchServiceImpl(userRepository, jobRepository);

        CLI cli = new CLI(userService, jobService, matchService);
        cli.readCommands();
    }
}