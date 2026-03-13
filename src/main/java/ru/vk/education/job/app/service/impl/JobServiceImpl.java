package ru.vk.education.job.app.service.impl;

import ru.vk.education.job.app.service.JobService;
import ru.vk.education.job.data.model.Job;
import ru.vk.education.job.data.repository.JobRepository;

import java.util.List;

public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job add(Job job) {
        return jobRepository.add(job);
    }

    @Override
    public List<Job> getAll() {
        return jobRepository.getAll();
    }
}
