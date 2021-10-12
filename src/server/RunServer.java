package server;

import server.model.MessageManager;
import server.model.MessageManagerImp;
import server.networking.SocketServer;

public class RunServer {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer(new MessageManagerImp());
        socketServer.startServer();
    }
}
