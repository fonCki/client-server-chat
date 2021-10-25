package client.networking;

import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.util.List;

public interface Client extends Subject {
    void startClient(User nickName);
    void newUser(String nickName);
    void newMessage(Message message);
    List<User> getUsers();
    void userLeft(User identity);
}
