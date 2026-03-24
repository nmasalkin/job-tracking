package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService {

    private final UserService userService;
    private final JobService jobService;
    private final MatchService matchService;
    private final FileService fileService;

    public CommandService(UserService userService, JobService jobService, MatchService matchService, FileService fileService) {
        this.userService = userService;
        this.jobService = jobService;
        this.matchService = matchService;
        this.fileService = fileService;
    }

    public void definition(String[] command) {
        definition(command, true);
    }

    public void definition(String[] command, boolean logging) {
        if (command.length == 0) {
            return;
        }
        if (command.length > 1) {
            switch (command[0]) {
                case "user":
                    addUser(command);
                    break;
                case "job":
                    addJob(command);
                    break;
                case "suggest":
                    suggest(command);
                    break;
                default:
                    break;
            }
        } else {
            switch (command[0]) {
                case "user-list":
                    getAllUsers();
                    break;
                case "job-list":
                    getAllJobs();
                    break;
                case "history":
                    history();
                    break;
                case "exit":
                    System.exit(0);
                default:
                    break;
            }
        }
        if (logging) {
            fileService.writeCommand(command);
        }
    }

    private void addUser(String[] command) {
        if (command.length != 4) {
            return;
        }
        String name = command[1];
        List<String> skills = new ArrayList<>();
        int exp = -1;
        for (int i = 2; i < command.length; i++) {
            if (command[i].contains("--skills=")) {
                if (!skills.isEmpty()) {
                    return;
                }
                String[] skillsArr = command[i].replace("--skills=", "").split(",");
                skills.addAll(Arrays.asList(skillsArr));
            } else if (command[i].contains("--exp=")) {
                if (exp != -1) {
                    return;
                }
                try {
                    exp = Integer.parseInt(command[i].replace("--exp=", ""));
                } catch (NumberFormatException e) {
                    return;
                }
            } else {
                return;
            }
        }
        User user = new User(name, skills.stream().distinct().sorted().toList(), exp);
        userService.add(user);
    }

    private void getAllUsers() {
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
    }

    private void addJob(String[] command) {
        if (command.length != 5) {
            return;
        }
        String title = command[1];
        String company = null;
        List<String> tags = new ArrayList<>();
        int exp = -1;
        for (int i = 2; i < command.length; i++) {
            if (command[i].contains("--company=")) {
                if (company != null) {
                    return;
                }
                company = command[i].replace("--company=", "");
            } else if (command[i].contains("--tags=")) {
                if (!tags.isEmpty()) {
                    return;
                }
                String[] tagsArr = command[i].replace("--tags=", "").split(",");
                tags.addAll(Arrays.asList(tagsArr));
            } else if (command[i].contains("--exp=")) {
                if (exp != -1) {
                    return;
                }
                try {
                    exp = Integer.parseInt(command[i].replace("--exp=", ""));
                } catch (NumberFormatException e) {
                    return;
                }
            }
        }
        Job job = new Job(title, company, tags.stream().distinct().sorted().toList(), exp);
        jobService.add(job);
    }

    private void getAllJobs() {
        for (Job job : jobService.getAll()) {
            System.out.println(job);
        }
    }

    private void suggest(String[] command) {
        if (command.length != 2) {
            return;
        }
        List<Job> jobs = matchService.suggest(command[1]);
        if (jobs == null) {
            return;
        }
        for (Job job : jobs) {
            System.out.println(job);
        }
    }

    private void history() {
        List<String> history = fileService.readCommands();
        for (String historyLine : history) {
            System.out.println(historyLine);
        }
    }
}
