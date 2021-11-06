package server.model;

import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.util.List;

public interface MessageManager extends Subject {
    void createUser(User user);
    void newMessage(Message message);
    List<User> getUsers();
    void removeUser(String ID);
}
