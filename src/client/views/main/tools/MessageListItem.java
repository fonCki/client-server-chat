package client.views.main.tools;

import shared.transferobjects.Message;

public class MessageListItem {
    String ID;
    String Message;

    public MessageListItem(shared.transferobjects.Message message) {
        this.ID = message.getReceiver().getID();
        Message = message.getContent();
    }

    public void addMessage(String message) {
        Message += "\n" + message;
    }

    @Override
    public String toString() {
        return "MessageListItem{" +
                "ID='" + ID + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
