package client.core;

import client.networking.Client;
import client.networking.SocketClient;

public class ClientFactory {
    private Client client;

    public ClientFactory() {
        client = new SocketClient();
    }

    public Client getClient() {
        return client;
    }
}
