package server.networking;

import server.model.MessageManager;
import shared.util.Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private MessageManager messageManager;

    public SocketServer(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void startServer() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(Connection.PORT);
            while (true) {
                Socket socket = welcomeSocket.accept();
                new Thread(new SocketHandler(socket, messageManager)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
