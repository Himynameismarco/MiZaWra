package com.project.mizawra.controllers.rest;

import com.project.mizawra.common.events.OnRegistrationCompleteEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {
    private final ApplicationEventPublisher eventPublisher;
    private final ClientService clientService;

    public RegisterRestController(ApplicationEventPublisher eventPublisher, ClientService clientService) {
        this.eventPublisher = eventPublisher;
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public String registerClient(@Valid ClientDto clientDto, HttpServletRequest request) throws Exception{
        Client client = clientService.registerClient(clientDto);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(client, request.getLocale()));
        return "success";
    }
}
