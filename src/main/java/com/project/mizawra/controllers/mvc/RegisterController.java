package com.project.mizawra.controllers.mvc;

import com.project.mizawra.common.EmailTemplateFactory;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final ClientService clientService;
    private final MessageSource messages;
    private final EmailTemplateFactory emailTemplateFactory;
    private final JavaMailSender mailSender;

    public RegisterController(ClientService clientService, MessageSource messages,
                              EmailTemplateFactory emailTemplateFactory, JavaMailSender mailSender) {
        this.clientService = clientService;
        this.messages = messages;
        this.emailTemplateFactory = emailTemplateFactory;
        this.mailSender = mailSender;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("client", new ClientDto());
        return "register";
    }

    @GetMapping("/confirm")
    public String registerConfirmation(@RequestParam("token") String token, HttpServletRequest request, Model model) {
        VerificationToken verificationToken = clientService.getVerificationToken(token);
        Locale locale = request.getLocale();
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "message";
        }

        Client client = verificationToken.getClient();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            VerificationToken newToken = clientService.regenerateVerificationToken(verificationToken);
            mailSender.send(emailTemplateFactory.getRegisteredTokenRegenerated(newToken, request.getLocale()));

            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "message";
        }

        client.setActive(true);
        clientService.save(client);
        clientService.deleteVerificationToken(verificationToken.getToken());
        return "redirect:/login";
    }
}
