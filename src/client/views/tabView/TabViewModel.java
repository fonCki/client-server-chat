package client.views.tabView;

import client.model.MessageModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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


    public TabViewModel(MessageModel messageModel) {
        this.messageModel = messageModel;
        this.message = new SimpleStringProperty("");
        this.support = new PropertyChangeSupport(this);

        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        support.firePropertyChange(event);
    }



    public void sendMessage(String text, User receiver) {
        this.receiver = receiver;
        messageModel.newMessage(text, receiver);
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
