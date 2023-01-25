package com.project.mizawra.dao;

import com.project.mizawra.models.Prompt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, UUID> {
}
