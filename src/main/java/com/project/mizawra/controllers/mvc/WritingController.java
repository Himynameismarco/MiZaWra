package com.project.mizawra.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WritingController {
    @RequestMapping("/promptedJournal")
    public String getPromptedJournal() {
        return "promptedJournal";
    }

    @RequestMapping("/story")
    public String getStory() {
        return "story";
    }

    @RequestMapping("/write")
    public String getWritingPage(@RequestParam(name = "mode", required = false) String mode) {
        return "writing";
    }
}
