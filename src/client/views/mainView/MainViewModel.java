package client.views.mainView;

import client.model.MessageModel;
import client.views.mainView.tools.UsersSimplified;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel implements Subject {

    private PropertyChangeSupport support;

    private MessageModel messageModel;
    private ObservableList<UsersSimplified> users;


    public MainViewModel(MessageModel messageModel) {
        this.users = new SimpleListProperty<>();
        this.support = new PropertyChangeSupport(this);
        this.messageModel = messageModel;
        messageModel.addListener("USER_LIST_MODIFIED", this::onUserListModified);
        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        if (newMessage.getReceiver() != null) {
            if (newMessage.getReceiver().equals(messageModel.getIdentity())) {
                support.firePropertyChange("CREATE_NEW_TAB", null, newMessage);
            }
        }
    }


    private void onUserListModified(PropertyChangeEvent event) {
        if (!users.isEmpty()) {
            users.clear();
            for (UsersSimplified user: getSortedList()) {
                users.add(user);
            }
        }
    }

    public void loadOnlineUsers() { //CHECK THIS, MAYBE PUT IN THE CONSTRUCTOR
        this.users = FXCollections.observableArrayList(getSortedList());
    }

    public ObservableList<UsersSimplified> getSimplifiedUsers() {
        return users;
    }

    public List<User> getUserList() {
        return messageModel.getUsers();
    }

    private List<UsersSimplified> getSortedList() {
        List<User> tempList = messageModel.getUsers();
        Collections.sort(tempList, (User u1, User u2) ->{
            return u1.getNickName().compareToIgnoreCase(u2.getNickName());
        });
        List<UsersSimplified> newList = new ArrayList<>();
        for (User user: tempList) {
            newList.add(new UsersSimplified(user));
        }
        return newList;
    }

    public void userLeft() {
        messageModel.userLeft();
    }


    public User getIdentity() {
        return messageModel.getIdentity();
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




