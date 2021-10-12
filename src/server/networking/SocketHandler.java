package server.networking;

import server.model.MessageManager;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;

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
            if (request.getType().equals("LISTENER")) {
                messageManager.addListener("USER_LIST_MODIFIED", this::onListModified);
                messageManager.addListener("NEW_MESSAGE", this::onNewMessage);
            } else if (request.getType().equals("NEW_USER")) {
                String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
                String nickName = (String) request.getArg();
                User newUser = new User(nickName,ip);
                messageManager.addUser(newUser);
                outToClient.writeObject(new Request("CONNECTED", newUser.copy()));
            } else if (request.getType().equals("GET_USERS")) {
                outToClient.writeObject(new Request("FETCH_USERS", messageManager.getUsers()));
            } else if (request.getType().equals("NEW_MESSAGE")) {
                System.out.println((Message) request.getArg());
                messageManager.newMessage((Message) request.getArg());
                outToClient.writeObject(new Request("NEW_MESSAGE", (Message) request.getArg()));
            }
        } catch (IOException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    private void onNewMessage(PropertyChangeEvent event) {
        try {
            outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
        } catch (IOException e) {
            e.printStackTrace();
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
