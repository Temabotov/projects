package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class StartServer extends Thread {
    public StartServer() {
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2526);
            MultiThreadServer server = new MultiThreadServer(serverSocket);
            System.out.println("Server is up and running");
            System.out.println("Server IP: " + serverSocket.getInetAddress() + "\nPort: " + serverSocket.getLocalPort());
            server.run();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}

