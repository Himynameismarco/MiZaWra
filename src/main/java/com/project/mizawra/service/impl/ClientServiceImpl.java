package com.project.mizawra.service.impl;

import com.project.mizawra.dao.ClientRepository;
import com.project.mizawra.dao.VerificationTokenRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, VerificationTokenRepository verificationTokenRepository,
                             PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client getClient(String email) {
        return clientRepository.findByEmail(email).orElse(null);
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

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void changeClientPassword(Client client, String newPassword) {
        client.setPassword(passwordEncoder.encode(newPassword));
        save(client);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public VerificationToken createVerificationToken(String token, TokenType type, Client client) {
        return verificationTokenRepository.save(new VerificationToken(token, type, client));
    }

    @Override
    public VerificationToken regenerateVerificationToken(VerificationToken token) {
        Client client = token.getClient();

        verificationTokenRepository.delete(token);
        return createVerificationToken(UUID.randomUUID().toString(), token.getType(), client);
    }

    @Override
    public void deleteVerificationToken(String token) {
        verificationTokenRepository.deleteAllByToken(token);
    }

    @Override
    public boolean isTokenExpired(VerificationToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }
}
