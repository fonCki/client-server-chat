package client.views.main;

import client.model.MessageModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel {
    private MessageModel messageModel;
    private ObservableList<User> users;
    private StringProperty message;

    public MainViewModel(MessageModel messageModel) {
        this.messageModel = messageModel;
        this.message = new SimpleStringProperty("");
        messageModel.addListener("USER_LIST_MODIFIED", this::onUserListModified);
        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
       if (newMessage.getReceiver() == null) {
            message.setValue(message.getValue() + "\n" +
                    newMessage.getSender().getNickName() + "> "
                    + newMessage.getContent());
        }
    }

    private void onUserListModified(PropertyChangeEvent event) {
        users.clear();
        for (User user : getSortedList()) {
            users.add(user);
        }
    }

    public void loadOnlineUsers() {
        this.users = FXCollections.observableArrayList(getSortedList());
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void sendMessage(String text) {
        messageModel.newMessage(text, null);
    }

    public StringProperty messageProperty() {
        return message;
    }

    private List<User> getSortedList() {
        List<User> newList = messageModel.getUsers();
        Collections.sort(newList, (User u1, User u2) ->{
            return u1.getNickName().compareToIgnoreCase(u2.getNickName());
        });
        return newList;
    }

    public void userLeft() {
        messageModel.userLeft();
    }

    public void userSelected(User userSelected) {
    }

    public User getIdentity() {
        return messageModel.getIdentity();
    }
}
