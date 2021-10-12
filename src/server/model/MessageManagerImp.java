package server.model;

import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    public void newMessage(Message message) {
        support.firePropertyChange("NEW_MESSAGE", null, message);
    }

    public synchronized void addUser(User user) {
        users.add(user);
      //  for (User user: users) {
        //    System.out.println(user.getNickName());
       // }
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
