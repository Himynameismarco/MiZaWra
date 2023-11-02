package com.project.mizawra.rest;

import com.project.mizawra.models.Journal;
import com.project.mizawra.models.Prompt;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.models.dto.PromptDto;
import com.project.mizawra.service.JournalService;
import com.project.mizawra.service.PromptService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private final PromptService promptService;

    public JournalsController(JournalService journalService, PromptService promptService) {
        this.journalService = journalService;
        this.promptService = promptService;
    }

    @GetMapping("/prompt")
    public PromptDto getRandomPromptByMode(@RequestParam String mode) {
        return convertPromptToDto(promptService.getRandomByModeAndLocale(mode, Locale.ENGLISH));
    }

    @PostMapping("/save")
    public JournalDto saveJournal(@RequestBody JournalDto journalDto) throws Exception {
        return convertJournalToDto(journalService.save(journalDto));
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

    private PromptDto convertPromptToDto(Prompt prompt) {
        return new PromptDto(prompt.getId().toString(), prompt.getPrompt());
    }
}
