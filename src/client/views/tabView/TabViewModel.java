package client.views.tabView;

import client.model.MessageModel;
import client.views.main.tools.MessageList;
import client.views.main.tools.MessageListItem;
import client.views.main.tools.PrivateMessage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TabViewModel implements Subject {

    private PropertyChangeSupport support;

    private MessageModel messageModel;
    private StringProperty message;
    private User receiver;

    private MessageList messageList;
    ObservableList<MessageListItem> messageListItems;

    public TabViewModel(MessageModel messageModel) {
        this.support = new PropertyChangeSupport(this);
        this.messageList = new MessageList();
        this.messageModel = messageModel;
        this.message = new SimpleStringProperty("");

        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        support.firePropertyChange(event);
    }

    private void showMessage(Message newMessage) {
        message.setValue(message.getValue() + "\n" +
                newMessage);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void sendMessage(String text, User receiver) {
        this.receiver = receiver;
        System.out.println(receiver);
        messageModel.newMessage(text, receiver);
    }

    public ObservableList<MessageListItem> getMessageListItems() {
        return messageListItems;
    }

    public void loadOMessages() {
        this.messageListItems = FXCollections.observableArrayList(messageList.getListMessage());
    }

    @Override
    public void addListener(String evt, PropertyChangeListener listener) {
        support.addPropertyChangeListener(evt, listener);
    }

    @Override
    public void removeListener(String evt, PropertyChangeListener listener) {
        support.removePropertyChangeListener(evt, listener);

    }

    public User getIdentity() {
        return messageModel.getIdentity();
    }
}
