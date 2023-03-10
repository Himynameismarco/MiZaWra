package com.project.mizawra.service;

import com.project.mizawra.models.Prompt;

public interface PromptService {
    Prompt getRandomByMode(String mode);
}
