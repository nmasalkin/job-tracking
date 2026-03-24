package ru.vk.education.job;

import ru.vk.education.job.app.cli.CLI;
import ru.vk.education.job.app.service.*;
import ru.vk.education.job.data.repository.JobRepository;
import ru.vk.education.job.data.repository.UserRepository;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("log.txt");

        JobRepository jobRepository = new JobRepository();
        UserRepository userRepository = new UserRepository();

        JobService jobService = new JobService(jobRepository);
        UserService userService = new UserService(userRepository);
        MatchService matchService = new MatchService(userService, jobService);
        FileService fileService = new FileService(path);
        CommandService commandService = new CommandService(userService, jobService, matchService, fileService);

        CLI cli = new CLI(commandService, fileService);
        cli.runCommandsFromFile();
        cli.run();
    }
}