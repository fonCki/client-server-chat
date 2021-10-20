package client.views.main.tools;

import shared.transferobjects.Message;
import shared.transferobjects.User;

public class PrivateMessage {
    private Message message;
    private User receiver;

    public PrivateMessage(Message message, User receiver) {
        this.message = message;
        this.receiver = receiver;
    }

    public Message getMessage() {
        return message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
