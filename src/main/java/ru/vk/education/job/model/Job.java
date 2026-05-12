package ru.vk.education.job.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Job {

    private UUID id;
    private String title;
    private String company;
    private List<String> tags;
    private int experience;
    private LocalDateTime createdAt;

    public Job() {
    }

    public Job(String title, String company, List<String> tags, int experience) {
        this.title = title;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
    }

    public Job(UUID id, String title, String company, List<String> tags, int experience, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title +
               " at " +
               company;
    }
}
