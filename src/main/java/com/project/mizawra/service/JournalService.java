package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import java.util.List;

public interface JournalService {
    List<Journal> getJournals();
    Journal save(JournalDto journalDto);
    long countByOwner(Client owner);
}
