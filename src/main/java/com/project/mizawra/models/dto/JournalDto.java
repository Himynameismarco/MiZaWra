package com.project.mizawra.models.dto;

public class JournalDto {
    private String mode;
    private String title;
    private String body;

    public JournalDto() {
    }

    public JournalDto(String mode, String title, String body) {
        this.mode = mode;
        this.title = title;
        this.body = body;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
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
}
