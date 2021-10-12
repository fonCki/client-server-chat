package server.model;

import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.util.List;

public interface MessageManager extends Subject {
    void addUser(User user);
    List<User> getUsers();

    void newMessage(Message message);
}
