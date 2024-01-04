package com.project.mizawra.models.dto;

public class PromptDto {
    private String id;
    private String mode;
    private String prompt;

    public PromptDto() {
    }

    public PromptDto(String id, String mode, String prompt) {
        this.id = id;
        this.mode = mode;
        this.prompt = prompt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
