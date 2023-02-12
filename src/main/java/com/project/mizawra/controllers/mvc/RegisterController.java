package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.context.MessageSource;
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

    public RegisterController(ClientService clientService, MessageSource messages) {
        this.clientService = clientService;
        this.messages = messages;
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
            return "badUser";
        }

        Client client = verificationToken.getClient();
        if (verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "badUser";
        }

        client.setActive(true);
        clientService.save(client);
        return "redirect:/login";
    }
}
