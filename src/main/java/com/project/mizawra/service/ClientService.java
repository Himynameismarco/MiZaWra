package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;

public interface ClientService {
    Client registerClient(ClientDto clientDto) throws Exception;
    Client save(Client client);

    VerificationToken getVerificationToken(String token);
    VerificationToken createVerificationToken(String token, TokenType type, Client client);
    VerificationToken regenerateVerificationToken(VerificationToken token);
    void deleteVerificationToken(String token);
}
