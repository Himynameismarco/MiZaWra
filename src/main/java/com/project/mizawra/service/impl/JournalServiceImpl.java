package com.project.mizawra.service.impl;

import com.project.mizawra.dao.JournalRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import com.project.mizawra.service.PromptService;
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
    private final PromptService promptService;
    private final JournalRepository journalRepository;
    private static final Integer JOURNALS_SIZE = 9;

    public JournalServiceImpl(ClientService clientService, PromptService promptService,
                              JournalRepository journalRepository) {
        this.clientService = clientService;
        this.promptService = promptService;
        this.journalRepository = journalRepository;
    }

    @Override
    public Optional<Journal> get(UUID id) {
        return journalRepository.findById(id);
    }

    @Override
    public List<Journal> getJournals(int page) {
        Client client = clientService.getAuthenticatedClient();
        Pageable pageable = PageRequest.of(page, JOURNALS_SIZE, Sort.by("postedDate").descending());
        return journalRepository.findAllByOwner(client, pageable);
    }

    @Override
    public Long getPageCount() {
        Client client = clientService.getAuthenticatedClient();
        return journalRepository.countByOwner(client) / JOURNALS_SIZE + 1;
    }

    @Override
    public Journal save(JournalDto journalDto) throws Exception {
        Journal journal;

        if (StringUtils.hasText(journalDto.getId()) && get(UUID.fromString(journalDto.getId())).isPresent()) {
            journal = get(UUID.fromString(journalDto.getId())).get();
            journal.setTitle(journalDto.getTitle());
            journal.setBody(journalDto.getBody());
        } else {
            journal = new Journal(journalDto);
            if (StringUtils.hasText(journalDto.getPromptDto().getId())) {
                journal.setPrompt(promptService.get(UUID.fromString(journalDto.getPromptDto().getId())));
            }
            journal.setOwner(clientService.getAuthenticatedClient());
        }

        return journalRepository.save(journal);
    }

    @Override
    public void delete(UUID id) {
        journalRepository.deleteById(id);
    }

    @Override
    public Long countJournalsForAuthenticatedUser() {
        return journalRepository.countByOwner(clientService.getAuthenticatedClient());
    }
}
