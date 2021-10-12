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
import java.util.List;

public class MainViewModel {
    private MessageModel messageModel;
    private ObservableList<User> users;
    private StringProperty message;

    public MainViewModel(MessageModel messageModel) {
        this.messageModel = messageModel;
        this.message = new SimpleStringProperty("");
        //users = FXCollections.observableArrayList(new ArrayList<User>());
        //loadOnlineUsers();
        messageModel.addListener("USER_LIST_MODIFIED", this::onNewUser);
        messageModel.addListener("NEW_MESSAGE", this::onNewMessage);
     //   request = new SimpleStringProperty("");
     //   reply = new SimpleStringProperty("");
    }

    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        message.setValue(message.getValue() + "\n" +
                         newMessage.getSender().getNickName() + "> "
                         + newMessage.getContent());
    }

    private void onNewUser(PropertyChangeEvent event) {

        List<User> newList = messageModel.getUsers();
        // List<User> logList = messageModel.getUsers();
        // users.add(((List<User>) event.getNewValue()).get(((List<User>) event.getNewValue()).size() -1));
        users.clear();
        for (User user : newList) {
            System.out.println(user.getCreated() + " From main");
            System.out.println(user.getID() + " From main");
            users.add(user);
            //    loadOnlineUsers(event);
        }
    }

    public void loadOnlineUsers() {
      //  List<User> list = new ArrayList<>()
        users = FXCollections.observableArrayList(messageModel.getUsers());
    //    for (User user: users) {
    //        System.out.println(user.getNickName());
     //   }
    }

    public ObservableList<User> getOnlineUsers() {
        return users;
    }

    public void sendMessage(String text) {
        messageModel.sendMessage(text);
    }

    public StringProperty messageProperty() {
        return message;
    }

    //   public void send() {
  //      messageModel.send();
  //  }

 //   public void loadOnlineUsers() {
  //  }

  //  public ObservableList<String> getOnlineUsers() {
   // }
}
