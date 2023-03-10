package com.project.mizawra.controllers.mvc;

import com.project.mizawra.service.PromptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WritingController {
    private final PromptService promptService;

    public WritingController(PromptService promptService) {
        this.promptService = promptService;
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
            model.addAttribute("prompt", promptService.getRandomByMode(mode));
        }
        return "writing";
    }
}
