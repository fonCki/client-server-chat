package client.networking;

import shared.transferobjects.Avatar;
import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Client extends Subject {
    void startClient(String nickName, Avatar avatar);
    void newMessage(Message message);
    List<User> getUsers();
    void userLeft(User identity);
}
