package client.model;

import client.networking.Client;
import com.sun.javafx.geom.transform.Identity;
import com.sun.javafx.tk.DummyToolkit;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class MessageModelManager implements MessageModel{

    private PropertyChangeSupport support;
    private Client client;
    private User identity = null;

    public MessageModelManager(Client client) {
        this.support = new PropertyChangeSupport(this);
        this.client = client;
        client.addListener("USER_LIST_MODIFIED", this::onNewUser);
        client.addListener("NEW_MESSAGE", this::onNewMessage);
        client.addListener("NEW_USER", this::onNewName);
    }

    private void onNewName(PropertyChangeEvent event) {
        identity = ((User) event.getNewValue()).copy();
        System.out.println("My identity is:" + identity);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        System.out.println("de aca " + (Message) event.getNewValue());
        support.firePropertyChange(event);
    }


    @Override
    public List<User> getUsers() {
        return client.getUsers();
    }

    @Override
    public void onNewUser(String nickName) {
        client.startClient(nickName);
    }

    @Override
    public void sendMessage(String text) {
        Message newMessage = new Message(text, identity);
        client.newMessage(newMessage);
    }

    private void onNewUser(PropertyChangeEvent event) {
        support.firePropertyChange(event);
   }

    @Override
    public void addListener(String evt, PropertyChangeListener listener) {
        support.addPropertyChangeListener(evt, listener);
    }

    @Override
    public void removeListener(String evt, PropertyChangeListener listener) {
        support.removePropertyChangeListener(evt, listener);

    }

}
