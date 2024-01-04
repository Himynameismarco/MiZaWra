package com.project.mizawra.service.impl;

import com.project.mizawra.dao.VerificationTokenRepository;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.service.VerificationTokenService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
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

    @Transactional
    @Override
    public void deleteVerificationToken(String token) {
        verificationTokenRepository.deleteAllByToken(token);
    }

    @Override
    public boolean isTokenExpired(VerificationToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }
}
