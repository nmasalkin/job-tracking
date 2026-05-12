package ru.vk.education.job.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.model.User;

import java.time.LocalDateTime;
import java.util.*;


@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            Arrays.asList((String[]) rs.getArray("skills").getArray()),
            rs.getInt("experience"),
            rs.getObject("created_at", LocalDateTime.class)
    );

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User add(User user) {
        return jdbcTemplate.queryForObject("""
                        insert into users (name, skills, experience)
                        values (:name, :skills, :experience)
                        returning *
                        """,
                Map.of(
                        "name", user.getName(),
                        "skills", user.getSkills().toArray(new String[0]),
                        "experience", user.getExperience()
                ),
                userRowMapper
        );
    }

    public User getById(UUID id) {
        return jdbcTemplate.queryForObject("""  
                        select * from users
                        where id = :id
                        """,
                Map.of("id", id),
                userRowMapper);
    }

    public UUID getIdByName(String name) {
        return jdbcTemplate.queryForList("""
                        select id from users
                        where name = :name
                        """,
                Map.of("name", name),
                UUID.class
        ).stream().findFirst().orElse(null);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("""
                        select * from users
                        """,
                userRowMapper
        );
    }
}
