package ru.vk.education.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.User;
import ru.vk.education.job.service.JobService;
import ru.vk.education.job.service.SuggestService;
import ru.vk.education.job.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class SuggestServiceIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SuggestService suggestService;

    @Test
    public void suggestIntegrationTest() {
        User user = userService.add(new User("testUser", List.of("java", "git"), 4));
        User user1 = userService.add(new User("testUser1", List.of("c", "git"), 2));
        User user2 = userService.add(new User("testUser2", List.of("java", "python"), 5));
        User user3 = userService.add(new User("testUser3", List.of("python", "c"), 3));

        Job job = jobService.add(new Job("testJob", "testCompany", List.of("java", "git"), 2));
        Job job1 = jobService.add(new Job("testJob1", "testCompany1", List.of("c", "git"), 3));
        Job job2 = jobService.add(new Job("testJob2", "testCompany2", List.of("java", "python"), 4));
        Job job3 = jobService.add(new Job("testJob3", "testCompany3", List.of("python", "c"), 1));

        List<Job> result = suggestService.suggest(user2.getName());
        List<Job> expected = List.of(job2, job);

        assertEquals(2, result.size());
        assertEquals(expected.get(0).getId(), result.get(0).getId());
        assertEquals(expected.get(1).getId(), result.get(1).getId());
    }
}