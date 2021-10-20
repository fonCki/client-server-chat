package client.model;

import javafx.scene.image.Image;
import shared.transferobjects.User;
import shared.util.Subject;

import java.awt.image.BufferedImage;
import java.util.List;

public interface MessageModel extends Subject {
    void newUser(String value, BufferedImage bufferedImage);
    void newMessage(String text, User receiver);
    List<User> getUsers();
    void userLeft();
    User getIdentity();

    Image getAvatar();
}
