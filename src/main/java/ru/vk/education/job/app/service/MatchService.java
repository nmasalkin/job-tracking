package ru.vk.education.job.app.service;

import ru.vk.education.job.data.model.Job;

import java.util.List;

public interface MatchService {

    List<Job> suggest(String name);
}
