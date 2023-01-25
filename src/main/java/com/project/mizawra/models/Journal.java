package com.project.mizawra.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Journal {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Client owner;
    private LocalDateTime postedDate;
    @Enumerated(EnumType.ORDINAL)
    private Mode mode;
    private String title;
    private String body;

    public Journal() {
    }

    public Journal(Client owner, LocalDateTime postedDate, Mode mode, String title, String body) {
        this.owner = owner;
        this.postedDate = postedDate;
        this.mode = mode;
        this.title = title;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Journal journal = (Journal) o;
        return id.equals(journal.id) && owner.equals(journal.owner) && Objects.equals(postedDate, journal.postedDate)
                && mode == journal.mode && Objects.equals(title, journal.title) && Objects.equals(body, journal.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, postedDate, mode, title, body);
    }

    @Override
    public String toString() {
        return "Journal{" + "id=" + id + ", owner=" + owner + ", postedDate=" + postedDate + ", mode=" + mode
                + ", title='" + title + '\'' + ", body='" + body + '\'' + '}';
    }
}
