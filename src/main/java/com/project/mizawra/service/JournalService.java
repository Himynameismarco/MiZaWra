package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;

public interface JournalService {
    Journal save(JournalDto journalDto);
    long countByOwner(Client owner);
}
