package ru.vk.education.job.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.model.Job;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JobRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Job> jobRowMapper = (rs, rowNum) -> new Job(
            UUID.fromString(rs.getString("id")),
            rs.getString("title"),
            rs.getString("company"),
            Arrays.asList((String[]) rs.getArray("tags").getArray()),
            rs.getInt("experience"),
            rs.getObject("created_at", LocalDateTime.class)
    );

    public JobRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Job add(Job job) {
        return jdbcTemplate.queryForObject("""
                        insert into jobs (title, company, tags, experience)
                        VALUES (:title, :company, :tags, :experience)
                        returning *
                        """,
                Map.of(
                        "title", job.getTitle(),
                        "company", job.getCompany(),
                        "tags", job.getTags().toArray(new String[0]),
                        "experience", job.getExperience()
                ),
                jobRowMapper
        );
    }

    public Job getById(UUID id) {
        return jdbcTemplate.queryForObject("""
                        select * from jobs
                        where id = :id
                        """,
                Map.of("id", id),
                jobRowMapper
        );
    }

    public UUID getIdByTitle(String title) {
        return jdbcTemplate.queryForList("""
                        select id from jobs
                        where title = :title
                        """,
                Map.of("title", title),
                UUID.class
        ).stream().findFirst().orElse(null);
    }

    public List<Job> getAll() {
        return jdbcTemplate.query("""
                        select * from jobs
                        """,
                jobRowMapper
        );
    }
}
