package com.project.mizawra.controllers.rest;

import com.project.mizawra.common.event.OnForgetPasswordEvent;
import com.project.mizawra.common.event.OnRegistrationCompleteEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.models.dto.SavePasswordDto;
import com.project.mizawra.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {
    private final ApplicationEventMulticaster eventMulticaster;
    private final ClientService clientService;

    public RegisterRestController(ApplicationEventMulticaster eventMulticaster, ClientService clientService) {
        this.eventMulticaster = eventMulticaster;
        this.clientService = clientService;
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestParam("email") String email, HttpServletRequest request) throws Exception{
        Client client = clientService.getClient(email);
        if (client == null) {
            throw new Exception("Email not found");
        }

        eventMulticaster.multicastEvent(new OnForgetPasswordEvent(client, request.getLocale()));
        return "success";
    }

    @PostMapping("/savePassword")
    public String savePassword(SavePasswordDto passwordDto) {
        VerificationToken token = clientService.getVerificationToken(passwordDto.getTokenId());
        if (token == null || clientService.isTokenExpired(token)) {
            return "fail";
        }
        clientService.changeClientPassword(token.getClient(), passwordDto.getPassword());
        clientService.deleteVerificationToken(passwordDto.getTokenId());
        return "success";
    }
}
