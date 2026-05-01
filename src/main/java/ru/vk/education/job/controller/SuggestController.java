package ru.vk.education.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vk.education.job.model.Job;
import ru.vk.education.job.service.SuggestService;

import java.util.List;

@RestController
@RequestMapping("/api/suggest")
public class SuggestController {

    private final SuggestService suggestService;

    public SuggestController(SuggestService suggestService) {
        this.suggestService = suggestService;
    }

    @GetMapping()
    public List<Job> suggest(@RequestParam String name) {
        return suggestService.suggest(name);
    }
}
