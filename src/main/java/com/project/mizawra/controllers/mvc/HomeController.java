package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Client;
import com.project.mizawra.service.ClientService;
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

    @GetMapping("/home")
    public String home(Model model) {
        Client client = clientService.getAuthenticatedClient();
        model.addAttribute("client", client);
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "message", required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "login";
    }
}
