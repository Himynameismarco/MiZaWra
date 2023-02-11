package com.project.mizawra.events;

import com.project.mizawra.models.Client;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private Client client;

    public OnRegistrationCompleteEvent(Client client) {
        super(client);
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
