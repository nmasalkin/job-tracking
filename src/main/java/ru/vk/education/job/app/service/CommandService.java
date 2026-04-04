package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.model.Match;
import ru.vk.education.job.data.model.User;

import java.util.*;

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
                case "stat":
                    stat(command);
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
        userService.getAll().stream().sorted(Comparator.comparing(User::getName)).forEach(System.out::println);
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
        jobService.getAll().stream().sorted(Comparator.comparing(Job::getTitle)).forEach(System.out::println);
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

    private void stat(String[] command) {
        if (command.length != 3) {
            return;
        }
        switch (command[1]) {
            case "--exp":
                try {
                    int n = Integer.parseInt(command[2]);
                    List<Job> jobs = jobService.getAll().stream()
                            .filter(job -> job.getExperience() >= n)
                            .sorted(Comparator.comparing(Job::getTitle)).toList();
                    jobs.forEach(System.out::println);
                } catch (NumberFormatException e) {
                    return;
                }
                break;
            case "--match":
                try {
                    int n = Integer.parseInt(command[2]);
                    List<User> users = userService.getAll().stream()
                            .filter(user -> matchService.suggest(user.getName()) != null
                                            && matchService.suggest(user.getName()).size() >= n).toList();
                    users.forEach(System.out::println);
                } catch (NumberFormatException e) {
                    return;
                }
                break;
            case "--top-skills":
                try {
                    int n = Integer.parseInt(command[2]);
                    HashMap<String, Integer> skills = new HashMap<>();
                    userService.getAll().forEach(user -> user.getSkills()
                            .forEach(skill -> skills.put(skill, skills.get(skill) != null ? skills.get(skill) + 1 : 1)));
                    int maxValue = skills.values().stream().max(Integer::compareTo).get();
                    List<String> top = new ArrayList<>();
                    while (maxValue > 0) {
                        int finalMaxValue = maxValue;
                        top.addAll(skills.keySet().stream()
                                .filter(key -> skills.get(key)
                                        .equals(finalMaxValue)).sorted().toList());
                        maxValue--;
                    }
                    for (int i = 0; i < n; i++) {
                        System.out.println(top.get(i));
                    }
                } catch (NumberFormatException e) {
                    return;
                }
                break;
        }
    }
}
