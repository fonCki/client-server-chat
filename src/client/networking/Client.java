package client.networking;

import shared.transferobjects.Message;
import shared.transferobjects.User;
import shared.util.Subject;

import java.util.List;

public interface Client extends Subject {
  //  void send(Request request);
    public List<User> getUsers();
    public void startClient(String nickName);

    boolean onNewUser(String nickName);

    void newMessage(Message message);
}
