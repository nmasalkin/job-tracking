package ru.vk.education.job.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job addJob(@RequestBody Job job) {
        return jobService.add(job);
    }

    @GetMapping
    public List<Job> getJobs() {
        return jobService.getAll();
    }
}
