package com.project.mizawra.models.dto;

public class JournalDto {
    private String id;
    private String promptId;
    private String title;
    private String body;
    private String postedDate;

    public JournalDto() {
    }

    public JournalDto(String id, String title, String body, String postedDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.postedDate = postedDate;
    }

    public JournalDto(String id, String promptId, String title, String body, String postedDate) {
        this.id = id;
        this.promptId = promptId;
        this.title = title;
        this.body = body;
        this.postedDate = postedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
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
