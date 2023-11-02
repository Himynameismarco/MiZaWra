package com.project.mizawra.models.dto;

public class PromptDto {
    private String id;
    private String prompt;

    public PromptDto() {
    }

    public PromptDto(String id, String prompt) {
        this.id = id;
        this.prompt = prompt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
