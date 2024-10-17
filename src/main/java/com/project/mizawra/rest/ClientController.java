package com.project.mizawra.rest;

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
        clientService.updateSettings(settingsDto);
        return ResponseEntity.ok().build();
    }
}
