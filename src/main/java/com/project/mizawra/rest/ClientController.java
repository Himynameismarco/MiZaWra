package com.project.mizawra.rest;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.Settings;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import org.springframework.http.ResponseEntity;
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
        return convertClientToDto(clientService.getAuthenticatedClient());
    }

    @PutMapping
    public ResponseEntity<Object> editClient(@RequestBody ClientDto clientDto) {
        clientService.edit(clientDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/settings")
    public Settings getSettings() {
        Settings clientSettings = clientService.getAuthenticatedClient().getSettings();
        if (clientSettings != null) {
            clientSettings.setId(null);
            clientSettings.setClient(null);
            return clientSettings;
        }
        return new Settings();
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

    private ClientDto convertClientToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        if (client != null) {
            clientDto.setFirstName(client.getFirstName());
            clientDto.setLastName(client.getLastName());
            clientDto.setEmail(client.getEmail());
        }
        return clientDto;
    }
}
