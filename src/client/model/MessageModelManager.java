package client.model;

import client.networking.Client;
import com.sun.javafx.geom.transform.Identity;
import com.sun.javafx.tk.DummyToolkit;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;


import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class MessageModelManager implements MessageModel{

    private PropertyChangeSupport support;
    private Client client;
    private User identity = null;
    private BufferedImage bufferedAvatar;
    private Image avatar;

    public MessageModelManager(Client client) {
        this.support = new PropertyChangeSupport(this);
        this.client = client;
        client.addListener("USER_LIST_MODIFIED", this::onUserListModified);
        client.addListener("NEW_MESSAGE", this::onNewMessage);
       // client.addListener("NEW_PRIVATE_MESSAGE", this::onNewMessage);
        client.addListener("NEW_USER", this::onNewUser);
    }

    private void onUserListModified(PropertyChangeEvent event) {
        support.firePropertyChange(event);
    }

    private void onNewUser(PropertyChangeEvent event) {
        identity = ((User) event.getNewValue()).copy();
        this.avatar = SwingFXUtils.toFXImage(bufferedAvatar, null);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        support.firePropertyChange(event);
    }


    @Override
    public List<User> getUsers() {
        return client.getUsers();
    }

    @Override
    public void userLeft() {
        client.userLeft(identity);
    }

    @Override
    public void newUser(String nickName, BufferedImage bufferedImage) {
        this.bufferedAvatar = bufferedImage;
        client.newUser(nickName);
        client.startClient(identity);
    }

    @Override
    public void newMessage(String text, User receiver) {
        Message newMessage = new Message(text, identity, receiver);
        client.newMessage(newMessage);
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
        return identity;
    }
    public Image getAvatar() {
        return avatar;
    }
}
