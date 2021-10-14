package client.views.privateView;

import client.model.MessageModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;

public class PrivateViewModel {
    MessageModel messageModel;
    StringProperty message;
    private User receiver;
    private Stage stage;

    public PrivateViewModel(MessageModel messageModel) {
        this.messageModel = messageModel;
        this.message = new SimpleStringProperty("");
        this.receiver = null;
        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
    }


    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        if (!(newMessage.getReceiver() == null)) {
            if (receiver == null) receiver = newMessage.getSender();
         if (newMessage.getReceiver().getID().equals(receiver.getID()) ||
             newMessage.getSender().getID().equals(receiver.getID())) {

             message.setValue(message.getValue() + "\n" +
                            newMessage.getSender().getNickName() + "> "
                            + newMessage.getContent());

             message.setValue(message.getValue() + "\n" + "-------------------" + "\n" + newMessage);
         }
        }
    }

    public void sendMessage(String text) {
        messageModel.newMessage(text,receiver);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void shareStage(Stage stage) {
        this.stage = stage;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;

    }
}
