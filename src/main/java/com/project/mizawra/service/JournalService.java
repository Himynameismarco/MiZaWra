package com.project.mizawra.service;

import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;

public interface JournalService {
    Journal save(JournalDto journalDto);
}
