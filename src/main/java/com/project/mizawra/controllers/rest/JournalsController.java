package com.project.mizawra.controllers.rest;

import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.JournalService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/get")
    public List<JournalDto> getJournals() {
        return journalService.getJournals().stream().map(this::convertJournalToDto).collect(Collectors.toList());
    }

    private JournalDto convertJournalToDto(Journal journal) {
        String mode = journal.getMode() != null ? journal.getMode().toString() : null;
        String postedDate = journal.getPostedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return new JournalDto(journal.getId().toString(), mode, journal.getTitle(), journal.getBody(), postedDate);
    }
}
