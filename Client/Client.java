package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static Client connection;
    private Socket clientSock;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String message;

    public Client() {
        try {
            this.clientSock = new Socket(InetAddress.getLocalHost(), 2526);
            this.outStream = new ObjectOutputStream(this.clientSock.getOutputStream());
            this.inStream = new ObjectInputStream(this.clientSock.getInputStream());
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public static Client getInstance() {
        if (connection == null) {
            connection = new Client();
        }
        return connection;
    }

    public void sendMessage(String message) {
        try {
            this.outStream.writeObject(message);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void sendObject(Object object) {
        try {
            this.outStream.flush();
            this.outStream.writeObject(object);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void sendObject1(Object object) {
        try {
            this.outStream.flush();
            this.outStream.writeObject(object);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public String readMessage() {
        try {
            this.message = (String)this.inStream.readObject();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.message;
    }

    public Object readObject() {
        Object object = new Object();

        try {
            object = this.inStream.readObject();
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            this.clientSock.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}