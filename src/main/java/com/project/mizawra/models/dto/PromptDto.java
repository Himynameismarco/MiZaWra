package com.project.mizawra.models.dto;

import com.project.mizawra.models.Prompt;

public class PromptDto {
    private String id;
    private String mode;
    private String prompt;

    public PromptDto() {
    }

    public PromptDto(Prompt prompt) {
        if (prompt != null) {
            setId(prompt.getId().toString());
            setMode(prompt.getMode().toString());
            setPrompt(prompt.getPrompt());
        }
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
