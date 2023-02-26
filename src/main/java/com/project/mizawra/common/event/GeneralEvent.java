package com.project.mizawra.common.event;

import com.project.mizawra.models.Client;
import java.util.Locale;
import org.springframework.context.ApplicationEvent;

public class GeneralEvent extends ApplicationEvent {
    private Client client;
    private Locale locale;

    public GeneralEvent(Client client, Locale locale) {
        super(client);
        this.client = client;
        this.locale = locale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
