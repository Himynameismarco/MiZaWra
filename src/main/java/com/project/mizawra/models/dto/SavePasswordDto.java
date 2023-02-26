package com.project.mizawra.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SavePasswordDto {
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String tokenId;

    public SavePasswordDto() {
    }

    public SavePasswordDto(String password, String tokenId) {
        this.password = password;
        this.tokenId = tokenId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
