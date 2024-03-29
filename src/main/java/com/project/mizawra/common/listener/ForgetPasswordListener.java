package com.project.mizawra.common.listener;

import com.project.mizawra.common.EmailTemplateFactory;
import com.project.mizawra.common.event.OnForgetPasswordEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.TokenType;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.service.VerificationTokenService;
import java.util.UUID;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ForgetPasswordListener implements ApplicationListener<OnForgetPasswordEvent> {
    private final VerificationTokenService verificationTokenService;
    private final EmailTemplateFactory emailTemplateFactory;
    private final JavaMailSender javaMailSender;

    public ForgetPasswordListener(VerificationTokenService verificationTokenService, EmailTemplateFactory emailTemplateFactory,
                                  JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.emailTemplateFactory = emailTemplateFactory;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(OnForgetPasswordEvent event) {
        changePassword(event);
    }

    private void changePassword(OnForgetPasswordEvent event) {
        Client client = event.getClient();
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(UUID.randomUUID().toString(),
                TokenType.CHANGE_PASSWORD, client);

        javaMailSender.send(emailTemplateFactory.getForgetPassword(verificationToken, event.getLocale()));
    }
}
