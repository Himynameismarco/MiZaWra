package com.project.mizawra.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Settings {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;
    private Boolean lightTheme;
    private Long timer;
    private String locale;
    @JsonIgnore
    @OneToOne(mappedBy = "settings")
    private Client client;

    public Settings() {
        this.lightTheme = Boolean.TRUE;
        this.timer = 900000L;
        this.locale = "en";
    }

    public Settings(Boolean lightTheme, Long timer, String locale) {
        this.lightTheme = lightTheme;
        this.timer = timer;
        this.locale = locale;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getLightTheme() {
        return lightTheme;
    }

    public void setLightTheme(Boolean lightTheme) {
        this.lightTheme = lightTheme;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
