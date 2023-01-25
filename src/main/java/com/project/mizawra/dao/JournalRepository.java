package com.project.mizawra.dao;

import com.project.mizawra.models.Journal;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, UUID> {
}
