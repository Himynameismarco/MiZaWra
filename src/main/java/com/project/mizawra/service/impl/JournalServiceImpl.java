package com.project.mizawra.service.impl;

import com.project.mizawra.dao.JournalRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.Mode;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JournalServiceImpl implements JournalService {
    private final ClientService clientService;
    private final JournalRepository journalRepository;

    public JournalServiceImpl(ClientService clientService, JournalRepository journalRepository) {
        this.clientService = clientService;
        this.journalRepository = journalRepository;
    }

    @Override
    public Optional<Journal> get(UUID id) {
        return journalRepository.findById(id);
    }

    @Override
    public List<Journal> getJournals(int page) {
        Client client = clientService.getAuthenticatedClient();
        Pageable pageable = PageRequest.of(page, 9, Sort.by("postedDate").descending());
        return journalRepository.findAllByOwner(client, pageable);
    }

    @Override
    public Long getPageCount() {
        Client client = clientService.getAuthenticatedClient();
        return journalRepository.countByOwner(client) / 9 + 1;
    }

    @Override
    public Journal save(JournalDto journalDto) {
        Journal journal;

        if (StringUtils.hasText(journalDto.getId()) && get(UUID.fromString(journalDto.getId())).isPresent()) {
            journal = get(UUID.fromString(journalDto.getId())).get();
            journal.setTitle(journalDto.getTitle());
            journal.setBody(journalDto.getBody());
        } else {
            journal = convertDtoToEntity(journalDto);
            journal.setPostedDate(LocalDateTime.now());
            journal.setOwner(clientService.getAuthenticatedClient());
        }

        return journalRepository.save(journal);
    }

    @Override
    public long countByOwner(Client owner) {
        return journalRepository.countByOwner(owner);
    }

    private Journal convertDtoToEntity(JournalDto journalDto) {
        Journal journal = new Journal();

        journal.setMode(journalDto.getMode() != null ? Mode.valueOf(journalDto.getMode()) : null);
        journal.setTitle(journalDto.getTitle());
        journal.setBody(journalDto.getBody());

        return journal;
    }
}
