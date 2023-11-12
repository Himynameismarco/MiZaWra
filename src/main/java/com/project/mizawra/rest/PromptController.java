package com.project.mizawra.rest;

import com.project.mizawra.models.Prompt;
import com.project.mizawra.models.dto.PromptDto;
import com.project.mizawra.service.PromptService;
import java.util.Locale;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
public class PromptController {
    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public PromptDto getPromptById(@RequestParam UUID id) {
        return convertPromptToDto(promptService.get(id));
    }

    @GetMapping("/random")
    public PromptDto getRandomPromptByMode(@RequestParam String mode) {
        return convertPromptToDto(promptService.getRandomByModeAndLocale(mode, Locale.ENGLISH));
    }

    private PromptDto convertPromptToDto(Prompt prompt) {
        return new PromptDto(prompt.getId().toString(), prompt.getPrompt());
    }
}
