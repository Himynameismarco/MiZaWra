package com.project.mizawra.service;

import com.project.mizawra.models.Prompt;
import java.util.Locale;
import java.util.UUID;

public interface PromptService {
    Prompt get(UUID id);
    Prompt getRandomByModeAndLocale(String mode, Locale locale);
}
