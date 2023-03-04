package com.project.mizawra.common.listener;

import com.project.mizawra.common.EmailTemplateFactory;
import com.project.mizawra.common.event.OnRegistrationCompleteEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.service.ClientService;
import java.util.UUID;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final ClientService clientService;
    private final EmailTemplateFactory emailTemplateFactory;
    private final JavaMailSender javaMailSender;

    public RegistrationListener(ClientService clientService, EmailTemplateFactory emailTemplateFactory,
                                JavaMailSender javaMailSender) {
        this.clientService = clientService;
        this.emailTemplateFactory = emailTemplateFactory;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Client client = event.getClient();
        VerificationToken verificationToken = clientService.createVerificationToken(UUID.randomUUID().toString(),
                TokenType.REGISTRATION, client);

        javaMailSender.send(emailTemplateFactory.getClientRegistered(verificationToken, event.getLocale()));
    }
}
