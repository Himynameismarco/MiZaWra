package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Client;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ClientService clientService;
    private final JournalService journalService;

    public HomeController(ClientService clientService, JournalService journalService) {
        this.clientService = clientService;
        this.journalService = journalService;
    }

    @GetMapping("/")
    public String root() {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Client client = clientService.getAuthenticatedClient();
        model.addAttribute("client", client);
        model.addAttribute("countJournals", journalService.countByOwner(client));
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "message", required = false) String message, Model model) {
        SecurityContextHolder.clearContext();
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "login/login";
    }
}
