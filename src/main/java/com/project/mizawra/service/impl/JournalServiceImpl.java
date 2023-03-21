package com.project.mizawra.service.impl;

import com.project.mizawra.common.CipherUtil;
import com.project.mizawra.dao.JournalRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.Journal;
import com.project.mizawra.models.dto.JournalDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import com.project.mizawra.service.PromptService;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
        Pageable pageable = PageRequest.of(page, 9, Sort.by("postedDate").descending());
        return journalRepository.findAllByOwner(client, pageable);
    }

    @Override
    public Long getPageCount() {
        Client client = clientService.getAuthenticatedClient();
        return journalRepository.countByOwner(client) / 9 + 1;
    }

    @Override
    public Journal save(JournalDto journalDto) throws Exception {
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

    private Journal convertDtoToEntity(JournalDto journalDto) throws Exception{
        Journal journal = new Journal();

        if (StringUtils.hasText(journalDto.getPromptId())) {
            journal.setPrompt(promptService.get(UUID.fromString(journalDto.getPromptId())));
        }
        journal.setTitle(journalDto.getTitle());
        journal.setBody(journalDto.getBody());

        return journal;
    }
}
