package com.project.mizawra.events;

import com.project.mizawra.models.Client;
import com.project.mizawra.service.ClientService;
import java.util.UUID;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final ClientService clientService;
    private final JavaMailSender javaMailSender;

    public RegistrationListener(ClientService clientService, JavaMailSender javaMailSender) {
        this.clientService = clientService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Client client = event.getClient();
        String token = UUID.randomUUID().toString();
        clientService.createVerificationToken(token, client);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(client.getEmail());
        emailMessage.setSubject("Registration Confirmation");
        emailMessage.setText("Please confirm your registration: " + System.getenv("DOMAIN")
                + "/register/confirm?token=" + token);
        javaMailSender.send(emailMessage);
    }
}
