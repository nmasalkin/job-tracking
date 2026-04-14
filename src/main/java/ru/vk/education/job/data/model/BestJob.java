package ru.vk.education.job.data.model;

public class BestJob {

    private User user;

    private Job job;

    public BestJob(User user, Job job) {
        this.user = user;
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public String toString() {
        return String.format("%s, лучшее предложение — %s в %s",
                user.getName(), job.getTitle(), job.getCompany());
    }
}
