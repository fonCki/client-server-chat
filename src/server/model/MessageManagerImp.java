package server.model;

import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageManagerImp implements MessageManager{

    private PropertyChangeSupport support;
    private List<User> users;


    public MessageManagerImp() {
        support = new PropertyChangeSupport(this);
        users = new ArrayList<>();
    }

    public synchronized List<User> getUsers() {
        return users;
    }

    @Override
    public void removeUser(String ID) {
        for (User user:users) {
            if (user.getID().equals(ID)) {
                users.remove(user);
                break;
            }
        }
        support.firePropertyChange("USER_LIST_MODIFIED", null, null);
        System.out.println(users);
    }

    @Override
    public void newMessage(Message message) {
        support.firePropertyChange("NEW_MESSAGE", null, message);
    }

    public synchronized void newUser(User user) {
        users.add(user);
        support.firePropertyChange("USER_LIST_MODIFIED", null, null);
    }

    @Override
    public void addListener(String evt, PropertyChangeListener listener) {
        support.addPropertyChangeListener(evt,listener);
    }

    @Override
    public void removeListener(String evt, PropertyChangeListener listener) {
        support.removePropertyChangeListener(evt,listener);
    }
}
