package client.networking;

import shared.networking.ClientCallBack;
import shared.networking.RMIServer;
import shared.transferobjects.Avatar;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIClient implements Client, ClientCallBack {

    private RMIServer server;
    private PropertyChangeSupport support;

    public RMIClient() {
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient(String nickName, Avatar avatar) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (RMIServer) registry.lookup("MessageServer");
            createUser(nickName, avatar);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void createUser(String nickName, Avatar avatar) {
        User identity = null;
        try {
            identity = server.createUser(nickName, avatar);
            server.registerClient(this, identity.copy());
            support.firePropertyChange("NEW_USER", null, identity);
        } catch (RemoteException e) {
            System.out.println("couldn't create the user/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void newMessage(Message message) {
        try {
            server.newMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getUsers() {
        try {
            return server.getUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void userLeft(User identity) {
        try {
            server.removeUser(identity.getID());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Request request) {
        support.firePropertyChange(request.getType(), null, request.getArg());
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