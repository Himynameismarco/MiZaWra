package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Settings;
import com.project.mizawra.models.dto.ClientDto;

public interface ClientService {
    Client getAuthenticatedClient();
    Client getClient(String email);
    Client registerClient(ClientDto clientDto);
    Client save(Client client);
    Client edit(ClientDto clientDto);
    void changeClientPassword(Client client, String newPassword);
    void updateSettings(Settings settings);
}
