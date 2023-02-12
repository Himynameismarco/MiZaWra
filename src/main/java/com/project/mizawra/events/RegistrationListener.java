package com.project.mizawra.events;

import com.project.mizawra.models.Client;
import com.project.mizawra.service.ClientService;
import java.util.UUID;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final ClientService clientService;
    private final MessageSource messages;
    private final JavaMailSender javaMailSender;

    public RegistrationListener(ClientService clientService, MessageSource messages, JavaMailSender javaMailSender) {
        this.clientService = clientService;
        this.messages = messages;
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
        String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String confirmationUrl = System.getenv("DOMAIN") + "/register/confirm?token=" + token;
        emailMessage.setText(message + confirmationUrl);
        javaMailSender.send(emailMessage);
    }
}
