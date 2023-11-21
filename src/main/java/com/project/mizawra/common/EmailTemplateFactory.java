package com.project.mizawra.common;

import com.project.mizawra.models.VerificationToken;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateFactory {
    private final MessageSource messages;

    public EmailTemplateFactory(MessageSource messages) {
        this.messages = messages;
    }

    public SimpleMailMessage getClientRegistered(VerificationToken token, Locale locale) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        String message = messages.getMessage("email.regSucc", null, locale);
        String confirmationUrl = System.getenv("DOMAIN") + "/register/activate?token=" + token.getToken();

        emailMessage.setTo(token.getClient().getEmail());
        emailMessage.setSubject("Registration Confirmation");
        emailMessage.setText(message + confirmationUrl);

        return emailMessage;
    }

    public SimpleMailMessage getRegisteredTokenRegenerated(VerificationToken token, Locale locale) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        String message = messages.getMessage("email.tokenRegen", null, locale);
        String confirmationUrl = System.getenv("DOMAIN") + "/register/activate?token=" + token.getToken();

        emailMessage.setTo(token.getClient().getEmail());
        emailMessage.setSubject("Token regeneration");
        emailMessage.setText(message + confirmationUrl);

        return emailMessage;
    }

    public SimpleMailMessage getForgetPassword(VerificationToken token, Locale locale) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        String message = messages.getMessage("email.forgetPassword", null, locale);
        String confirmationUrl = System.getenv("DOMAIN") + "/setNewPassword?token=" + token.getToken();

        emailMessage.setTo(token.getClient().getEmail());
        emailMessage.setSubject("ForgetPassword");
        emailMessage.setText(message + confirmationUrl);

        return emailMessage;
    }
}
