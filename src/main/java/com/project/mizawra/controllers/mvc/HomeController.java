package com.project.mizawra.controllers.mvc;

import com.project.mizawra.models.Client;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JournalService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/")
public class HomeController {
    private final ClientService clientService;
    private final JournalService journalService;

    public HomeController(ClientService clientService, JournalService journalService) {
        this.clientService = clientService;
        this.journalService = journalService;
    }

    @GetMapping("/api")
    public String root() {
        return "home";
    }

    @GetMapping(value = "/api/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> home(Model model) {

        //Client client = clientService.getAuthenticatedClient();
        Client client = clientService.getClient("kontakt@marcozander.com");

        // Create a response object
        Map<String, Object> response = new HashMap<>();
        response.put("client", client);
        response.put("countJournals", journalService.countByOwner(client));

        // Return the response object as JSON
        return ResponseEntity.ok(response);
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
