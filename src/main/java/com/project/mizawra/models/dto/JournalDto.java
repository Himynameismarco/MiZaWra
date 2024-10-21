package com.project.mizawra.models.dto;

import com.project.mizawra.models.Journal;
import java.time.format.DateTimeFormatter;

public class JournalDto {
    private String id;
    private PromptDto promptDto;
    private String title;
    private String body;
    private String postedDate;

    public JournalDto() {
    }

    public JournalDto(Journal journal) throws Exception {
        this.id = journal.getId().toString();
        this.title = journal.getTitle();
        this.body = journal.getBody();
        this.postedDate = journal.getPostedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (journal.getPrompt() != null) {
            this.promptDto = new PromptDto(journal.getPrompt());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PromptDto getPromptDto() {
        return promptDto;
    }

    public void setPromptDto(PromptDto promptDto) {
        this.promptDto = promptDto;
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

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }
}
