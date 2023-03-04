package com.project.mizawra.common.event;

import com.project.mizawra.models.Client;
import java.util.Locale;

public class OnRegistrationCompleteEvent extends GeneralEvent {
    public OnRegistrationCompleteEvent(Client client, Locale locale) {
        super(client, locale);
    }
}
