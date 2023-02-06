package com.project.mizawra.service.impl;

import com.project.mizawra.dao.ClientRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client registerClient(ClientDto clientDto) throws Exception {
        if (clientRepository.findByEmail(clientDto.getEmail()).isPresent()) {
            //todo change to custom exception later
            throw new Exception("User already exist.");
        }

        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(passwordEncoder.encode(clientDto.getPassword()));

        return clientRepository.save(client);
    }
}
