package client.views.users;

import client.model.MessageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class UsersViewModel {
    private ObservableList<User> users;
    private MessageModel messageModel;

    public UsersViewModel(MessageModel messageModel) {

     //   users.add(new Users("Lacambra"));
    //    this.messageModel = messageModel;
    //    messageModel.addListener("USER_LIST_MODIFIED", this::onNewUser);
    }

    private void onNewUser(PropertyChangeEvent event) {
 //       System.out.println("Al  FIIINN");
   //     loadOnlineUsers();
      //  System.out.println(((Users) event.getNewValue()).getNickName() + "Ju;ian");
     //   System.out.println(users + "Lina");
      //  users.add((Users) event.getNewValue());
    }
/*
    public void loadOnlineUsers() {
        List<User> logList = messageModel.getUsers();
        users = FXCollections.observableArrayList(logList);
        System.out.println(users + "DEMARIA");
        for (User user: users) {
            System.out.println(user.getNickName());
        }
    }

    public ObservableList<User> getOnlineUsers() {
        return users;
    }

 */
}
