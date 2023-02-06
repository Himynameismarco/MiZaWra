package com.project.mizawra.controllers.rest;

import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final ClientService clientService;

    public RegisterController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public String registerClient(@Valid ClientDto clientDto) throws Exception{
        clientService.registerClient(clientDto);
        return "success";
    }
}
