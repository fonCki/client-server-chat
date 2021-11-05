package client.core;

import client.networking.Client;
import client.networking.RMIClient;

public class ClientFactory {
    private Client client;

    public ClientFactory() {
        client = new RMIClient();
    }

    public Client getClient() {
        return client;
    }
}
