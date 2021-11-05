package server.networking;

import server.model.MessageManager;
import shared.networking.ClientCallBack;
import shared.networking.RMIServer;
import shared.transferobjects.Message;
import shared.transferobjects.Request;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServerImp implements RMIServer {

    private MessageManager messageManager;

    public RMIServerImp(MessageManager messageManager) throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        this.messageManager = messageManager;
    }

    public void startSever() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("MessageServer", this);
    }

    @Override
    public User newUser(String nickName) {
        User newUser = new User(nickName);
        messageManager.newUser(newUser);
        return newUser;
    }

    @Override
    public void newMessage(Message message) {
        messageManager.newMessage(message);

    }

    @Override
    public List<User> getUsers() {
        return messageManager.getUsers();
    }

    @Override
    public void removeUser(String ID) {
        messageManager.removeUser(ID);
    }

    @Override
    public void registerClient(ClientCallBack client, User identity) {
        messageManager.addListener("USER_LIST_MODIFIED", event -> onListModified(event, client));
        messageManager.addListener("NEW_MESSAGE", event -> onNewMessage(event, client, identity.getID()));
    }

    private void onNewMessage(PropertyChangeEvent event, ClientCallBack client, String ID) {
        if (((Message) event.getNewValue()).getReceiver() == null) {
            try {
                client.update(new Request(event.getPropertyName(), event.getNewValue()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (((Message) event.getNewValue()).getReceiver().getID().equals(ID)  ||
                ((Message) event.getNewValue()).getSender().getID().equals(ID))
        try {
            client.update(new Request(event.getPropertyName(), event.getNewValue()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onListModified(PropertyChangeEvent event, ClientCallBack client) {
        try {
            client.update(new Request("USER_LIST_MODIFIED", null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
