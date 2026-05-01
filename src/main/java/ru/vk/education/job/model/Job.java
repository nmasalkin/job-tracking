package ru.vk.education.job.model;

import java.util.List;

public class Job {

    private String title;

    private String company;

    private List<String> tags;

    private int experience;

    public Job(String title, String company, List<String> tags, int experience) {
        this.title = title;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return title +
               " at " +
               company;
    }
}
