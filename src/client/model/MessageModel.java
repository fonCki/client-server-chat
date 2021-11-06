package client.model;

import shared.transferobjects.Avatar;
import shared.transferobjects.User;
import shared.util.Subject;
import java.util.List;

public interface MessageModel extends Subject {
    void newUser(String nickName, Avatar avatar);
    void newMessage(String text, User receiver);
    List<User> getUsers();
    void userLeft();
    User getIdentity();
}
