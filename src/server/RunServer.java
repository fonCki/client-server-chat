package server;

import server.model.MessageManagerImp;
import server.networking.RMIServerImp;

import java.rmi.RemoteException;

public class RunServer {
    public static void main(String[] args) throws RemoteException {
        RMIServerImp rmiServer = new RMIServerImp(new MessageManagerImp());
        rmiServer.startSever();
    }
}
