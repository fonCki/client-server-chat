package shared.networking;

import shared.transferobjects.Avatar;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServer extends Remote {
    User createUser(String nickName, Avatar avatar) throws RemoteException;
    void newMessage(Message message) throws RemoteException;
    List<User> getUsers() throws RemoteException;
    void removeUser(String ID) throws RemoteException;
    void receiveImage(byte[] rawImage) throws RemoteException;
    void registerClient(ClientCallBack client, User identity) throws RemoteException;
   // void unRegisterClient(ClientCallBack client) throws RemoteException;
}
