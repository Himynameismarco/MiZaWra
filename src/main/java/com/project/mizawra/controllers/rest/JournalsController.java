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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalsController {
    private final JournalService journalService;

    public JournalsController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping("/save")
    public JournalDto saveJournal(JournalDto journalDto) {
        journalService.save(journalDto);
        return convertJournalToDto(journalService.save(journalDto));
    }

    @GetMapping("/get")
    public List<JournalDto> getJournals(@RequestParam(name = "page", required = false) Integer page) {
        int iPage = page != null ? page : 0;
        return journalService.getJournals(iPage).stream().map(this::convertJournalToDto).collect(Collectors.toList());
    }

    @GetMapping("/pages")
    public Long getPages() {
        return journalService.getPageCount();
    }

    private JournalDto convertJournalToDto(Journal journal) {
        String postedDate = journal.getPostedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return new JournalDto(journal.getId().toString(), journal.getTitle(), journal.getBody(), postedDate);
    }
}
