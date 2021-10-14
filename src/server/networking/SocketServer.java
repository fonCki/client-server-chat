package server.networking;

import server.model.MessageManager;
import shared.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private MessageManager messageManager;
    private List<Thread> activeUsers;

    public SocketServer(MessageManager messageManager) {
        activeUsers = new ArrayList<>();
        this.messageManager = messageManager;
    }

    public void startServer() {

        try {
            ServerSocket welcomeSocket = new ServerSocket(Connection.PORT);
            while (true) {
                Socket socket = welcomeSocket.accept();
                new Thread(new SocketHandler(socket, messageManager)).start();


                //     activeUsers.add(new Thread(new SocketHandler(socket, messageManager)));
            //    activeUsers.get(activeUsers.size()-1).start();
          //      new Thread(this::keepActiveThreads).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*

    private synchronized void keepActiveThreads() {
        while (true) {
            System.out.println(activeUsers.size());
            System.out.println(activeUsers.get(2).isAlive());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
            for (Thread thread: activeUsers) {
                if (!(thread.getName().contains("#ID"))) {
                    activeUsers.remove(thread);
                    break;
                } else if (!(thread.isAlive())) {
                    messageManager.removeUser(thread.getName());
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        */

}
