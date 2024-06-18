package com.project.mizawra.rest;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Settings;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ClientDto getAuthenticatedClient() {
        return new ClientDto(clientService.getAuthenticatedClient());
    }

    @PutMapping
    public ResponseEntity<Object> editClient(@RequestBody ClientDto clientDto) {
        try {
            clientService.edit(clientDto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/settings")
    public Settings getSettings() {
        return clientService.getAuthenticatedClient().getSettings();
    }

    @PutMapping("/settings")
    public ResponseEntity<Object> updateSettings(@RequestBody Settings settingsDto) {
        Client client = clientService.getAuthenticatedClient();
        Settings clientSettings = client.getSettings();

        if (clientSettings == null) {
            client.setSettings(settingsDto);
        } else {
            if (settingsDto.getLightTheme() != null) {
                clientSettings.setLightTheme(settingsDto.getLightTheme());
            }
            if (settingsDto.getTimer() != null) {
                clientSettings.setTimer(settingsDto.getTimer());
            }
            if (settingsDto.getLocale() != null) {
                clientSettings.setLocale(settingsDto.getLocale());
            }
        }

        clientService.save(client);

        return ResponseEntity.ok().build();
    }
}
