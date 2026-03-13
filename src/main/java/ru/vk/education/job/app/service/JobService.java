package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;

import java.util.List;

public interface JobService {

    Job add(Job job);

    List<Job> getAll();
}
