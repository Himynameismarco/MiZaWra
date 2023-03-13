package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JournalService {
    Optional<Journal> get(UUID id);
    List<Journal> getJournals(int page);
    Long getPageCount();
    Journal save(JournalDto journalDto);
    long countByOwner(Client owner);
}
