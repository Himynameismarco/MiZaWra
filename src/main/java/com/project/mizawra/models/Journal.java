package com.project.mizawra.models;

import com.project.mizawra.common.CipherUtil;
import com.project.mizawra.models.dto.JournalDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Entity
public class Journal {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Client owner;
    private LocalDateTime postedDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;
    private String title;
    private String body;

    public Journal() {
    }

    public Journal(Client owner, LocalDateTime postedDate, Prompt prompt, String title, String body) {
        this.owner = owner;
        this.postedDate = postedDate;
        this.prompt = prompt;
        this.title = title;
        this.body = body;
    }

    public Journal(JournalDto journalDto)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.title = journalDto.getTitle();
        this.setBody(journalDto.getBody());
        this.postedDate = LocalDateTime.now();
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

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody()
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        return body == null ? null : CipherUtil.decryptString(body);
    }

    public void setBody(String body)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        this.body = CipherUtil.encryptString(body);
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
                && Objects.equals(prompt, journal.prompt) && Objects.equals(title, journal.title) && Objects.equals(
                body, journal.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, postedDate, prompt, title, body);
    }

    @Override
    public String toString() {
        return "Journal{" + "id=" + id + ", owner=" + owner + ", postedDate=" + postedDate + ", prompt=" + prompt
                + ", title='" + title + '\'' + ", body='" + body + '\'' + '}';
    }
}
