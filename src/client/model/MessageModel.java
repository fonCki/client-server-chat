package client.model;

import shared.transferobjects.User;
import shared.util.Subject;

import java.util.List;

public interface MessageModel extends Subject {
   // void send(Request request);
    List<User> getUsers();

    void onNewUser(String value);

    void sendMessage(String text);
}
