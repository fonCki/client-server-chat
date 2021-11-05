package shared.networking;

import shared.transferobjects.Request;
import shared.transferobjects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallBack extends Remote {
    void update(Request request) throws RemoteException;
}
