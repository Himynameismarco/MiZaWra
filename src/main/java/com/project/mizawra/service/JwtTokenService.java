package com.project.mizawra.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String createToken(String username);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
}
