package com.project.mizawra.rest;

import com.project.mizawra.common.EmailTemplateFactory;
import com.project.mizawra.common.event.OnRegistrationCompleteEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final ApplicationEventMulticaster eventMulticaster;
    private final ClientService clientService;
    private final JavaMailSender mailSender;
    private final EmailTemplateFactory emailTemplateFactory;

    public RegisterController(ApplicationEventMulticaster eventMulticaster, ClientService clientService,
                              JavaMailSender mailSender, EmailTemplateFactory emailTemplateFactory) {
        this.eventMulticaster = eventMulticaster;
        this.clientService = clientService;
        this.mailSender = mailSender;
        this.emailTemplateFactory = emailTemplateFactory;
    }

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody @Valid ClientDto clientDto, HttpServletRequest request) {
        Client client = clientService.registerClient(clientDto);
        eventMulticaster.multicastEvent(new OnRegistrationCompleteEvent(client, request.getLocale()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<Object> registerActivation(@RequestParam("token") String token, HttpServletRequest request) {
        VerificationToken verificationToken = clientService.getVerificationToken(token);
        if (verificationToken == null) {
            return ResponseEntity.notFound().build();
        }

        Client client = verificationToken.getClient();
        if (clientService.isTokenExpired(verificationToken)) {
            VerificationToken newToken = clientService.regenerateVerificationToken(verificationToken);
            mailSender.send(emailTemplateFactory.getRegisteredTokenRegenerated(newToken, request.getLocale()));

            return ResponseEntity.badRequest().build();
        }

        client.setActive(true);
        clientService.save(client);
        clientService.deleteVerificationToken(verificationToken.getToken());
        return ResponseEntity.ok().build();
    }
}
