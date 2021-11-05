package server;

import server.model.MessageManager;
import server.model.MessageManagerImp;
import server.networking.RMIServerImp;
import server.networking.SocketServer;
import shared.networking.RMIServer;

import java.rmi.RemoteException;

public class RunServer {
    public static void main(String[] args) throws RemoteException {
        RMIServerImp rmiServer = new RMIServerImp(new MessageManagerImp());
        rmiServer.startSever();

    }


}
