package client.core;

import client.model.MessageModel;
import client.model.MessageModelManager;

public class ModelFactory {
    private ClientFactory clientFactory;
    private MessageModel messageModel;

    public ModelFactory(ClientFactory clientFactory) {
        messageModel = new MessageModelManager(clientFactory.getClient());
        this.clientFactory = clientFactory;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }
}
