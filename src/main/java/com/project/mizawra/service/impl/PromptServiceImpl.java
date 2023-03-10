package com.project.mizawra.service.impl;

import com.project.mizawra.dao.PromptRepository;
import com.project.mizawra.models.Mode;
import com.project.mizawra.models.Prompt;
import com.project.mizawra.service.PromptService;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class PromptServiceImpl implements PromptService {
    private final PromptRepository promptRepository;

    public PromptServiceImpl(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Override
    public Prompt getRandomByMode(String mode) {
        List<Prompt> promptList = promptRepository.findByMode(Mode.valueOf(mode));
        if (!promptList.isEmpty()) {
            return promptList.get(new Random().nextInt(promptList.size()));
        }
        return null;
    }
}
