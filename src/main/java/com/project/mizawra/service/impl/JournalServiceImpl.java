package com.project.mizawra.service.impl;

import com.project.mizawra.dao.JournalRepository;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.Mode;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService {
    private final ClientService clientService;
    private final JournalRepository journalRepository;

    public JournalServiceImpl(ClientService clientService, JournalRepository journalRepository) {
        this.clientService = clientService;
        this.journalRepository = journalRepository;
    }

    @Override
    public Journal save(JournalDto journalDto) {
        Journal journal = convertDtoToEntity(journalDto);

        journal.setPostedDate(LocalDateTime.now());
        journal.setOwner(clientService.getAuthenticatedClient());

        return journalRepository.save(journal);
    }

    private Journal convertDtoToEntity(JournalDto journalDto) {
        Journal journal = new Journal();

        if (journalDto.getMode() != null) {
            journal.setMode(Mode.valueOf(journalDto.getMode()));
        }
        journal.setTitle(journalDto.getTitle());
        journal.setBody(journalDto.getBody());

        return journal;
    }
}
