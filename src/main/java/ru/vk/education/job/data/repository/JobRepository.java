package ru.vk.education.job.data.repository;

import ru.vk.education.job.data.model.Job;

import java.util.ArrayList;
import java.util.List;

public class JobRepository {

    private final List<Job> jobs =  new ArrayList<>();

    public Job add(Job job) {
        if (findByTitle(job.getTitle()) != null) {
            return null;
        }
        jobs.add(job);
        return job;
    }

    public Job findByTitle(String title) {
        for (Job job : jobs) {
            if (job.getTitle().equals(title)) {
                return job;
            }
        }
        return null;
    }

    public List<Job> getAll () {
        return jobs;
    }
}
