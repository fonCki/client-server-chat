package client.views.main.tools;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferobjects.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
    private boolean added;
    private List<MessageListItem> listMessage;


    public MessageList() {
        listMessage = new ArrayList<>();
    }

    public void addMessage(Message message) {
        added = false;
        for (MessageListItem item : listMessage) {
            if (message.getReceiver().getID().equals(item.ID)) {
                item.addMessage(message.getContent());
                added = true;
                break;
            }
        }
        if (!added) {
            listMessage.add(new MessageListItem(message));
        }
        System.out.println(listMessage + "chas");
    }

    public List<MessageListItem> getListMessage() {
        return listMessage;
    }
}