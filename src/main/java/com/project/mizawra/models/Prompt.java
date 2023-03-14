package com.project.mizawra.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Prompt {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.ORDINAL)
    private Mode mode;
    private String prompt;
    @OneToMany(mappedBy = "prompt", fetch = FetchType.LAZY)
    private List<Journal> journals;

    public Prompt() {
    }

    public Prompt(Mode mode, String prompt) {
        this.mode = mode;
        this.prompt = prompt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prompt prompt1 = (Prompt) o;
        return id.equals(prompt1.id) && mode == prompt1.mode && Objects.equals(prompt, prompt1.prompt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, prompt);
    }

    @Override
    public String toString() {
        return "Prompt{" + "id=" + id + ", mode=" + mode + ", prompt='" + prompt + '\'' + '}';
    }
}
