package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Journal;
import com.project.mizawra.service.JournalService;
import com.project.mizawra.service.PromptService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WritingController {
    private final PromptService promptService;
    private final JournalService journalService;

    public WritingController(PromptService promptService, JournalService journalService) {
        this.promptService = promptService;
        this.journalService = journalService;
    }

    @RequestMapping("/promptedJournal")
    public String getPromptedJournal() {
        return "promptedJournal";
    }

    @RequestMapping("/story")
    public String getStory() {
        return "story";
    }

    @RequestMapping("/write")
    public String getWritingPage(@RequestParam(name = "mode", required = false) String mode, Model model) {
        if (mode != null) {
            model.addAttribute("mode", mode);
            model.addAttribute("prompt",
                    promptService.getRandomByModeAndLocale(mode, LocaleContextHolder.getLocale()));
        }
        return "writing";
    }

    @RequestMapping("/edit")
    public String editJournal(@RequestParam(name = "journalId") UUID journalId, Model model) throws Exception {
        Optional<Journal> optionalJournal = journalService.get(journalId);
        if (optionalJournal.isPresent()) {
            Journal journal = optionalJournal.get();
            model.addAttribute("journalId", journalId);
            model.addAttribute("prompt", journal.getPrompt());
            model.addAttribute("title", journal.getTitle());
            model.addAttribute("body", journal.getBody());
        }
        return "writing";
    }
}
