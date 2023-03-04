package com.project.mizawra.common.event;

import com.project.mizawra.models.Client;
import java.util.Locale;

public class OnForgetPasswordEvent extends GeneralEvent {
    public OnForgetPasswordEvent(Client client, Locale locale) {
        super(client, locale);
    }
}
