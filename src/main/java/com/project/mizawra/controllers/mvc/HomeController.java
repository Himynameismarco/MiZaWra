package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.ClientDto;
import com.project.mizawra.service.ClientService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ClientService clientService;

    public HomeController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String root() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("client", new ClientDto());
        return "register";
    }

    @GetMapping("/register/confirm")
    public String registerConfirmation(@RequestParam("token") String token) {
        VerificationToken verificationToken = clientService.getVerificationToken(token);
        if (verificationToken == null) {
            System.out.println("token not found");
        }

        Client client = verificationToken.getClient();
        if (verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            System.out.println("token has expired");
        }

        client.setActive(true);
        clientService.save(client);
        return "redirect:/login";
    }
}
