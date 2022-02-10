package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    private ServerSocket serverSocket;

    MultiThreadServer(ServerSocket servSock) {
        this.serverSocket = servSock;
    }

    public void run() {
        try {
            while(true) {
                Socket clientSocket = this.serverSocket.accept();
                (new Thread(new Server(clientSocket))).start();
                System.out.println("client connected");
            }
        } catch (IOException var2) {
            System.err.println("Ошибка подключения");
        }
    }
}