package ru.vk.education.job.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private List<String> skills;
    private int experience;
    private LocalDateTime created_at;

    public User() {
    }

    public User(String name, List<String> skills, int experience) {
        this.name = name;
        this.skills = skills;
        this.experience = experience;
    }

    public User(UUID id, String name, List<String> skills, int experience, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.experience = experience;
        this.created_at = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String skill : skills) {
            sb.append(skill);
            sb.append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return name + " " +
               sb + " " +
               experience;
    }
}
