package com.project.mizawra.service.impl;

import com.project.mizawra.dao.ClientRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.Settings;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public Client getAuthenticatedClient() {
        if (!isAnonymous()) {
            return getClient(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        }

        return null;
    }

    @Override
    public Client getClient(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Client registerClient(ClientDto clientDto) {
        if (clientRepository.findByEmail(clientDto.getEmail()).isPresent()) {
            throw new BadCredentialsException("User already exist.");
        }

        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(passwordEncoder.encode(clientDto.getPassword()));

        return clientRepository.save(client);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client edit(ClientDto clientDto) {
        Client client = getAuthenticatedClient();
        client.setFirstName(clientDto.getFirstName() != null ? clientDto.getFirstName() : client.getFirstName());
        client.setLastName(clientDto.getLastName() != null ? clientDto.getLastName() : client.getLastName());

        if (clientDto.getOldPassword() != null && passwordEncoder.matches(clientDto.getOldPassword(), client.getPassword())) {
            client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        } else if (clientDto.getOldPassword() != null) {
            throw new BadCredentialsException("Wrong password");
        }

        return save(client);
    }

    @Override
    public void changeClientPassword(Client client, String newPassword) {
        client.setPassword(passwordEncoder.encode(newPassword));
        save(client);
    }

    @Override
    public void updateSettings(Settings settings) {
        Client client = getAuthenticatedClient();
        Settings clientSettings = client.getSettings();

        if (clientSettings == null) {
            client.setSettings(settings);
        } else {
            clientSettings.setLightTheme(settings.getLightTheme());
            clientSettings.setTimer(settings.getTimer());
            clientSettings.setLocale(settings.getLocale());
        }

        save(client);
    }

    private boolean isAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }
}
