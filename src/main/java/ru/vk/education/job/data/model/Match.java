package ru.vk.education.job.data.model;

public class Match {

    private User user;

    private Job job;

    private int score;

    public Match(User user, Job job) {
        this.user = user;
        this.job = job;
        this.score = calculateScore(user, job);
    }

    public User getUser() {
        return user;
    }

    public Job getJob() {
        return job;
    }

    public int getScore() {
        return score;
    }

    private int calculateScore(User user, Job job) {
        int score = 0;
        for(String tag : job.getTags()) {
            if (user.getSkills().contains(tag)) {
                score++;
            }
        }
        if (user.getExperience() < job.getExperience()) {
            score = score / 2;
        }
        return score;
    }
}
