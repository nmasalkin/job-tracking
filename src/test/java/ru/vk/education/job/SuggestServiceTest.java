package ru.vk.education.job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.model.User;
import ru.vk.education.job.service.JobService;
import ru.vk.education.job.service.SuggestService;
import ru.vk.education.job.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SuggestServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JobService jobService;

    @InjectMocks
    private SuggestService suggestService;

    @Test
    public void suggestTest() {
        User user = new User("testUser", List.of("java", "git"), 3);
        when(userService.findByName("testUser")).thenReturn(user);

        Job job = new Job("testJob", "testCompany", List.of("java", "git"), 2);
        Job job1 = new Job("testJob1", "testCompany1", List.of("c", "git"), 3);
        Job job2 = new Job("testJob2", "testCompany2", List.of("java", "python"), 4);
        Job job3 = new Job("testJob3", "testCompany3", List.of("python", "c"), 1);
        when(jobService.getAll()).thenReturn(List.of(job, job1, job2, job3));

        List<Job> result = suggestService.suggest("testUser");
        List<Job> expected = List.of(job, job1);

        assertEquals(expected, result);
    }

    @Test
    public void emptyVacanciesTest() {
        User user = new User("testUser", List.of("java", "git"), 3);
        when(userService.findByName("testUser")).thenReturn(user);

        when(jobService.getAll()).thenReturn(List.of());

        List<Job> result = suggestService.suggest("testUser");

        assertNull(result);
    }

    @Test
    public void singleVacancyTest() {
        User user = new User("testUser", List.of("java", "git"), 3);
        when(userService.findByName("testUser")).thenReturn(user);

        Job job = new Job("testJob", "testCompany", List.of("java", "git"), 2);
        when(jobService.getAll()).thenReturn(List.of(job));

        List<Job> result = suggestService.suggest("testUser");
        List<Job> expected = List.of(job);

        assertEquals(expected, result);
    }

    @Test
    public void userNotFoundTest() {
        when(userService.findByName("testUser")).thenReturn(null);

        List<Job> result = suggestService.suggest("testUser");

        assertNull(result);
    }

}
