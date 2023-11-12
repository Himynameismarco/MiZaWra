package com.project.mizawra.service.impl;

import com.project.mizawra.dao.PromptRepository;
import com.project.mizawra.models.Mode;
import com.project.mizawra.models.Prompt;
import com.project.mizawra.service.PromptService;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PromptServiceImpl implements PromptService {
    private final PromptRepository promptRepository;

    public PromptServiceImpl(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Override
    public Prompt get(UUID id) {
        return promptRepository.findById(id).orElse(new Prompt());
    }

    @Override
    public Prompt getRandomByModeAndLocale(String mode, Locale locale) {
        List<Prompt> promptList = promptRepository.findByModeAndLocale(Mode.valueOf(mode), locale.getLanguage());
        if (!promptList.isEmpty()) {
            return promptList.get(new Random().nextInt(promptList.size()));
        }
        return null;
    }
}
