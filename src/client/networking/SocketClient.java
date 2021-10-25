package client.networking;

import shared.util.Connection;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketClient implements Client{
    private PropertyChangeSupport support;

    public SocketClient() {
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient(User user) {
        try {
            Socket socket = new Socket(Connection.HOST, Connection.PORT);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            new Thread(() -> listenToServer(user, outToServer, inFromServer)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenToServer(User user, ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            outToServer.writeObject(new Request("LISTENER", user.getID()));
            while (true) {
                Request request = (Request) inFromServer.readObject();
                support.firePropertyChange(request.getType(), null, request.getArg());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("DISCONNECTED!");
    }




    @Override
    public void newUser(String nickName) {
        try {
            Request response = request("NEW_USER", nickName);
            support.firePropertyChange("NEW_USER", null, response.getArg());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection Error");
        }
    }

    @Override
    public void newMessage(Message message) {
        try {
            Request response = request("NEW_MESSAGE", message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getUsers() {
        try {
            Request response = request("GET_USERS", null);
            return (List<User>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void userLeft(User identity) {
        try {
            Request response = request("USER_LEFT", identity);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Request request(String type, Object arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(Connection.HOST, Connection.PORT);
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
        outToServer.writeObject(new Request(type, arg));
        Request request = (Request) inFromServer.readObject();
        socket.close();
        return request;
    }

    @Override
    public void addListener(String evt, PropertyChangeListener listener) {
        support.addPropertyChangeListener(evt, listener);
    }

    @Override
    public void removeListener(String evt, PropertyChangeListener listener) {
        support.removePropertyChangeListener(evt, listener);
    }

}
