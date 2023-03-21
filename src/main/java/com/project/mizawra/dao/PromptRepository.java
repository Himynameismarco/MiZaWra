package com.project.mizawra.dao;

import com.project.mizawra.models.Mode;
import com.project.mizawra.models.Prompt;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, UUID> {
    List<Prompt> findByModeAndLocale(Mode mode, String locale);
}
