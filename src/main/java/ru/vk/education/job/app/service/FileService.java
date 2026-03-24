package ru.vk.education.job.app.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService {

    private final Path path;

    public FileService(Path path) {
        this.path = path;
        createFile();
    }

    private void createFile() {
        try {
            Files.createFile(path);
        } catch (IOException e) {
        }
    }

    public void writeCommand(String[] command) {
        try {
            Files.writeString(
                    path,
                    String.join(" ", command) + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
        }
    }

    public List<String> readCommands() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            return null;
        }
    }
}
