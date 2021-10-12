package client.networking;

import shared.port;
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
    public void startClient(String nickName) {
        try {
            Socket socket = new Socket("localhost", port.PORT);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            if (onNewUser(nickName))
                new Thread(() -> listenToServer(socket, outToServer, inFromServer)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onNewUser(String nickName) {
        try {
            Request response = request("NEW_USER", nickName);
            support.firePropertyChange("NEW_USER", null, response.getArg());
            return response.getType().equals("CONNECTED");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void newMessage(Message message) {
        try {
            Request response = request("NEW_MESSAGE", message);
        //    support.firePropertyChange(response.getType(), null, response.getArg());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void listenToServer(Socket socket, ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            outToServer.writeObject(new Request("LISTENER", null));
            while (true) {
                Request request = (Request) inFromServer.readObject();
                //System.out.println((Message) request.getArg() + "otro gato");
                support.firePropertyChange(request.getType(), null, request.getArg());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("DISCONECTED!");
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


    private Request request(String type, Object arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", port.PORT);
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
