package ru.vk.education.job.cli;

import org.springframework.stereotype.Component;
import ru.vk.education.job.service.CommandService;
import ru.vk.education.job.service.FileService;

import java.util.Scanner;

@Component
public class CLI {

    private final CommandService commandService;
    private final FileService fileService;

    public CLI(CommandService commandService, FileService fileService) {
        this.commandService = commandService;
        this.fileService = fileService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (input.equals("exit")) {
                    return;
                }
                String[] command = input.split(" ");
                commandService.definition(command);
            }
        }
    }

    public void runCommandsFromFile() {
        String[] command;
        for (String line : fileService.readCommands()) {
            command = line.split(" ");
            if (command[0].equals("user") ||
                command[0].equals("job")) {
                commandService.definition(command, false);
            }
        }
    }
}
