package com.project.mizawra.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Journal {
    private UUID id;
    private User owner;
    private LocalDateTime postedDate;
    private Mode mode;
    private String title;
    private String body;

    public Journal() {
    }

    public Journal(User owner, LocalDateTime postedDate, Mode mode, String title, String body) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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
