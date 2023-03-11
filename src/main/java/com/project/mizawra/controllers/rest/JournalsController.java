package com.project.mizawra.controllers.rest;

import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.JournalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalsController {
    private final JournalService journalService;

    public JournalsController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping("/save")
    public String saveJournal(JournalDto journalDto) {
        journalService.save(journalDto);
        return "success";
    }
}
