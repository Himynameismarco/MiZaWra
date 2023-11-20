package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;

public interface ClientService {
    Client getAuthenticatedClient();
    Client getClient(String email);
    Client getClientById(String id);
    Client registerClient(ClientDto clientDto);
    Client save(Client client);
    void changeClientPassword(Client client, String newPassword);

    VerificationToken getVerificationToken(String token);
    VerificationToken createVerificationToken(String token, TokenType type, Client client);
    VerificationToken regenerateVerificationToken(VerificationToken token);
    void deleteVerificationToken(String token);
    boolean isTokenExpired(VerificationToken token);
}
