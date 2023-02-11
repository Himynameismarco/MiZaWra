package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;

public interface ClientService {
    Client registerClient(ClientDto clientDto) throws Exception;
    Client save(Client client);

    VerificationToken createVerificationToken(String token, Client client);
    VerificationToken getVerificationToken(String token);
}
