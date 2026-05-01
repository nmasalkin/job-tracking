package ru.vk.education.job.model;

import java.util.List;

public class User {

    private String name;

    private List<String> skills;

    private int experience;

    public User(String name, List<String> skills, int experience) {
        this.name = name;
        this.skills = skills;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String skill : skills) {
            sb.append(skill);
            sb.append(",");
        };
        sb.delete(sb.length() - 1, sb.length());
        return name + " " +
               sb + " " +
               experience;
    }
}
