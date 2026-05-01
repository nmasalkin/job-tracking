package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.repository.JobRepository;

import java.util.List;

@Service
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
