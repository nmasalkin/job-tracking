package ru.vk.education.job.repository;

import org.springframework.stereotype.Repository;
import ru.vk.education.job.model.BestJob;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BestJobRepository {

    private List<BestJob> bestJobs = new ArrayList<>();

    public void setBestJobs(List<BestJob> bestJobs) {
        this.bestJobs = bestJobs;
    }

    public List<BestJob> getBestJobs() {
        return bestJobs;
    }
}
