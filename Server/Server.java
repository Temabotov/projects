package Server;

import Tables.Trips;
import Tables.User;
import database.DBWorker;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    private static final String FILE = "client.txt";
    private Socket factSocket;
    private DBWorker db = DBWorker.getInstance();
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private static String currentUser;
    private String messServ = null;

    Server(Socket socket) throws IOException {
        this.factSocket = socket;
        try {
            this.inStream = new ObjectInputStream(this.factSocket.getInputStream());
            this.outStream = new ObjectOutputStream(this.factSocket.getOutputStream());
        } catch (IOException var3) {
            var3.printStackTrace();
        }
        String doing = this.getAddress() + "\ton\t" + (new Date()).toString();
        System.out.println(doing);
        this.writeInFile(doing);
    }

    public void close() {
        try {
            this.outStream.flush();
            this.inStream.close();
            this.outStream.close();
            this.factSocket.close();
        } catch (IOException var2) {
        }
    }

    private void sendMessage(String messServ) {
        try {
            this.outStream.flush();
            this.outStream.writeObject(messServ);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    private Object readObject() {
        Object object = null;
        try {
            object = this.inStream.readObject();
        } catch (Exception var3) {
            System.out.println("Нет данных в потоке");
        }
        return object;
    }

    private Object readObject1() {
        Object object = null;
        try {
            object = this.inStream.readObject();
        } catch (Exception var3) {
            System.out.println("Нет данных в потоке");
        }
        return object;
    }

    private void sendObject(Object object) {
        try {
            this.outStream.flush();
            this.outStream.writeObject(object);
        } catch (IOException var3) {
        }

    }

    private String getAddress() {
        return this.factSocket.getInetAddress().toString();
    }

    private void writeInFile(String doing) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("client.txt", true)));
        Throwable var3 = null;
        try {
            pw.println(doing);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (pw != null) {
                if (var3 != null) {
                    try {
                        pw.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    pw.close();
                }
            }
        }
    }

    public void run() {
        try {
            while((this.messServ = (String)this.inStream.readObject()) != null) {
                String var2 = this.messServ;
                byte var3 = -1;
                switch(var2.hashCode()) {
                    case -2107126635:
                        if (var2.equals("GoodsTax")) {
                            var3 = 4;
                        }
                        break;
                    case -1818601502:
                        if (var2.equals("SignIn")) {
                            var3 = 2;
                        }
                        break;
                    case -1646928965:
                        if (var2.equals("MakeProfile")) {
                            var3 = 10;
                        }
                        break;
                    case -1312005449:
                        if (var2.equals("ShowHistory")) {
                            var3 = 6;
                        }
                        break;
                    case -267726101:
                        if (var2.equals("MoneyTax")) {
                            var3 = 5;
                        }
                        break;
                    case -104699274:
                        if (var2.equals("Shutdown")) {
                            var3 = 0;
                        }
                        break;
                    case 73595753:
                        if (var2.equals("LogIn")) {
                            var3 = 1;
                        }
                        break;
                    case 811459346:
                        if (var2.equals("FindTax")) {
                            var3 = 9;
                        }
                        break;
                    case 1645247968:
                        if (var2.equals("DeleteTax")) {
                            var3 = 8;
                        }
                        break;
                    case 2122698:
                        if (var2.equals("Data")) {
                            var3 = 12;
                        }
                        break;
                    case 1676786167:
                        if (var2.equals("DeleteRecords")) {
                            var3 = 7;
                        }
                        break;
                    case 1261626731:
                        if (var2.equals("Pas_Date")) {
                            var3 = 11;
                        }
                        break;
                    case 2011214679:
                        if (var2.equals("CarTax")) {
                            var3 = 3;
                        }
                }

                User user;
                Trips trips;
                Trips trips1;
                switch(var3) {
                    case 0:
                        try {
                            this.sendObject(this.db.Phistory());
                        } catch (Exception var25) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var25);
                        }
                        break;
                    case 1:
                        user = (User)this.readObject();
                        try {
                            currentUser = user.getLogin();
                        } catch (NullPointerException var31) {
                            System.out.println("User is not found");
                        }
                        try {
                            if (this.db.logIN(user.getLogin(), user.getPassword())) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("Fail");
                            }
                        } catch (Exception var30) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var30);
                        }
                        break;
                    case 2:
                        user = (User)this.readObject();
                        try {
                            if (this.db.RegisterNewUser(user.getUsername(), user.getUserSurname(), user.getEmail(), user.getLogin(), user.getPassword(),user.getPhone())) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("Bad");
                            }
                        } catch (Exception var29) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var29);
                        }
                        break;
                    case 3:
                        trips = (Trips) this.readObject();
                        try {
                            if (this.db.deletetrips(trips)) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("bad");
                            }
                        } catch (Exception var24) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var24);
                        }
                        break;
                    case 4:
                        trips = (Trips) this.readObject();
                        try {
                            if (this.db.RegisterNewTrips(trips.getDate(), trips.getTime(), trips.getCity(), trips.getAddress(), trips.getAddress_prib(),trips.getPlace(),trips.getCar_brand(),trips.getPrice())) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("Bad");
                            }
                        } catch (Exception var26) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var26);
                        }
                        break;
                    case 5:
                        trips = (Trips) this.readObject();
                        trips1 = (Trips) this.readObject1();
                        try {
                            if (this.db.booking(trips,trips1)) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("Fail");
                            }
                        } catch (Exception var23) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var23);
                        }
                        break;
                    case 6:
                        trips = (Trips) this.readObject();
                        String dat = trips.getFiltr_date();
                        String cit = trips.getFiltr_city();
                        try {
                            this.sendObject(this.db.showHistory(cit,dat));
                        } catch (Exception var25) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var25);
                        }
                        break;
                    case 7:
                        try {
                            if (this.db.deleteRecord(currentUser)) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("bad");
                            }
                        } catch (Exception var24) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var24);
                        }
                        break;
                    case 8:
                        trips = (Trips) this.readObject();
                        try {
                            if (this.db.deleteTax(trips)) {
                                this.sendMessage("Good");
                            } else {
                                this.sendMessage("Fail");
                            }
                        } catch (Exception var23) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var23);
                        }
                        break;
                    case 9:
                        try {
                            this.sendObject(this.db.Dhistory());
                        } catch (Exception var25) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, (String)null, var25);
                        }
                        break;
                    case 10:
                        this.sendObject(this.db.userProfileInfo(currentUser));
                        break;
                    case 11:
                        trips = (Trips) this.readObject1();
                        this.sendObject(this.db.Pas_Date(trips));
                        break;
                    case 12:
                        trips = (Trips) this.readObject1();
                        this.sendObject(this.db.date(trips));
                        break;
                    default:
                        this.close();
                }
            }
        } catch (Exception var33) {
        } finally {
            this.disconnect();
        }
    }

    private void disconnect() {
        try {
            try {
                if (this.outStream != null) {
                    this.outStream.close();
                }
                if (this.inStream != null) {
                    this.inStream.close();
                }
                System.out.println(this.factSocket.getInetAddress() + " disconnecting");
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        } finally {
        }
    }
}