package server.networking;

import server.model.MessageManager;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;
import shared.transferobjects.UserRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketHandler implements Runnable{
    private Socket socket;
    private MessageManager messageManager;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public SocketHandler(Socket socket, MessageManager messageManager) {
        this.socket = socket;
        this.messageManager = messageManager;
        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            Request request = (Request) inFromClient.readObject();
            switch (request.getType()) {
                case "LISTENER": {
                 //   Thread.currentThread().setName((String) request.getArg());
                 //   System.out.println(Thread.currentThread().getName());
                    runListeners((String) request.getArg());
                    break;
                }
                case "NEW_USER": {
                    runNewUser((String) request.getArg());
                    break;
                }
                case "GET_USERS": {
                    runGetUsers();
                    break;
                }
                case "NEW_MESSAGE": {
                    runNewMessage((Message) request.getArg());
                    break;
                }
                case "USER_LEFT": {
                    runUserLeft((User) request.getArg());
                    break;
                }
                default:
                    throw new Exception("Command error...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runUserLeft(User user) throws IOException {
        messageManager.removeUser(user.getID());
        outToClient.writeObject(new Request("USER_LEFT", "COMMAND_RECEIVED"));
    }


    private void runNewMessage(Message message) throws IOException {
        messageManager.newMessage(message);
        outToClient.writeObject(new Request("NEW_MESSAGE", "MESSAGE_RECEIVED"));
    }

    private void runGetUsers() throws IOException {
        outToClient.writeObject(new Request("FETCH_USERS", messageManager.getUsers()));
    }

    private void runNewUser(String nickName) throws IOException {
        String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
        User newUser = new User(nickName, ip);
        messageManager.newUser(newUser);
        outToClient.writeObject(new Request("CONNECTED", newUser.copy()));
    }

    private void runListeners(String ID) {
        messageManager.addListener("USER_LIST_MODIFIED", this::onListModified);
        messageManager.addListener("NEW_MESSAGE", event -> onNewMessage(event, ID) );
    }

    private void onNewMessage(PropertyChangeEvent event, String ID) {
        try {
            if (((Message) event.getNewValue()).getReceiver() == null) {
                outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
            } else if (((Message) event.getNewValue()).getReceiver().getID().equals(ID)  ||
                        ((Message) event.getNewValue()).getSender().getID().equals(ID))
                outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void onListModified(PropertyChangeEvent event) {
        try {
           outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
