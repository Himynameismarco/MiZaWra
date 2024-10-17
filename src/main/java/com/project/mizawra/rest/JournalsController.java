package com.project.mizawra.rest;

import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.JournalService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping
    public JournalDto editJournal(@RequestParam(name = "journalId") UUID journalId) throws Exception {
        return new JournalDto(journalService.get(journalId).orElseThrow());
    }
    @PostMapping
    public JournalDto saveJournal(@RequestBody JournalDto journalDto) throws Exception {
        return new JournalDto(journalService.save(journalDto));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteJournal(@RequestParam(name = "journalId") UUID journalId) {
        journalService.delete(journalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public Long getNumberOfJournalsForAuthenticatedUser() {
        return journalService.countJournalsForAuthenticatedUser();
    }

    @GetMapping("/get")
    public List<JournalDto> getJournals(@RequestParam(name = "page", required = false) Integer page) throws Exception {
        int iPage = page != null ? page : 0;
        List<JournalDto> result = new ArrayList<>();
        for (Journal journal : journalService.getJournals(iPage)) {
            result.add(new JournalDto(journal));
        }
        return result;
    }

    @GetMapping("/pages")
    public Long getPages() {
        return journalService.getPageCount();
    }
}
