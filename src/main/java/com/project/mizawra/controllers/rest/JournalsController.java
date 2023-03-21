package com.project.mizawra.controllers.rest;

import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.JournalService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    public JournalDto saveJournal(JournalDto journalDto) throws Exception {
        journalService.save(journalDto);
        return convertJournalToDto(journalService.save(journalDto));
    }

    @GetMapping("/get")
    public List<JournalDto> getJournals(@RequestParam(name = "page", required = false) Integer page) throws Exception {
        int iPage = page != null ? page : 0;
        List<JournalDto> result = new ArrayList<>();
        for (Journal journal : journalService.getJournals(iPage)) {
            result.add(convertJournalToDto(journal));
        }
        return result;
    }

    @GetMapping("/pages")
    public Long getPages() {
        return journalService.getPageCount();
    }

    private JournalDto convertJournalToDto(Journal journal) throws Exception {
        String postedDate = journal.getPostedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return new JournalDto(journal.getId().toString(), journal.getTitle(), journal.getBody(), postedDate);
    }
}
