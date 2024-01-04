package com.project.mizawra.service;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;

public interface VerificationTokenService {
    VerificationToken getVerificationToken(String token);
    VerificationToken createVerificationToken(String token, TokenType type, Client client);
    VerificationToken regenerateVerificationToken(VerificationToken token);
    void deleteVerificationToken(String token);
    boolean isTokenExpired(VerificationToken token);
}
