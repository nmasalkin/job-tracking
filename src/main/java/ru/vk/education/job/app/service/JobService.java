package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.repository.JobRepository;

import java.util.List;

public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job add(Job job) {
        return jobRepository.add(job);
    }

    public List<Job> getAll() {
        return jobRepository.getAll();
    }
}
