package ru.vk.education.job.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CliRunner implements CommandLineRunner {

    private final CLI cli;

    public CliRunner(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void run(String... args) throws Exception {
        cli.runCommandsFromFile();
        Thread thread = new Thread(cli::run);
        thread.setDaemon(true);
        thread.start();
    }
}
