package com.project.mizawra.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @NotNull
    private LocalDateTime expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(@NotBlank String token, @NotNull Client client) {
        this.token = token;
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
        return id.equals(that.id) && token.equals(that.token) && client.equals(that.client) && expiryDate.equals(
                that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, client, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" + "id='" + id + '\'' + ", token='" + token + '\'' + ", client=" + client
                + ", expiryDate=" + expiryDate + '}';
    }
}
