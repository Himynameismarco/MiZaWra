package com.project.mizawra.models.dto;

public class JournalDto {
    private String id;
    private PromptDto promptDto;
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

    public JournalDto(String id, PromptDto promptDto, String title, String body, String postedDate) {
        this.id = id;
        this.promptDto = promptDto;
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
