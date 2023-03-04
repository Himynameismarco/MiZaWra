package com.project.mizawra.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 1;

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String token;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @Enumerated(EnumType.ORDINAL)
    private TokenType type;
    @NotNull
    private LocalDateTime expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(@NotBlank String token,TokenType type, @NotNull Client client) {
        this.token = token;
        this.type = type;
        this.client = client;
        this.expiryDate = LocalDateTime.now().plusDays(EXPIRATION);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerificationToken that = (VerificationToken) o;
        return id.equals(that.id) && token.equals(that.token) && client.equals(that.client) && type == that.type
                && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, client, type, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" + "id=" + id + ", token='" + token + '\'' + ", client=" + client + ", type=" + type
                + ", expiryDate=" + expiryDate + '}';
    }
}
