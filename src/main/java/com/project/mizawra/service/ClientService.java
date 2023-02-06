package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.dto.ClientDto;

public interface ClientService {
    Client registerClient(ClientDto clientDto) throws Exception;
}
