package ru.vk.education.job.data.repository;

import ru.vk.education.job.data.model.BestJob;

import java.util.ArrayList;
import java.util.List;

public class BestJobRepository {

    private List<BestJob> bestJobs = new ArrayList<>();

    public void setBestJobs(List<BestJob> bestJobs) {
        this.bestJobs = bestJobs;
    }

    public List<BestJob> getBestJobs() {
        return bestJobs;
    }
}
