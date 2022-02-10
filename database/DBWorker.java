package database;

import Tables.Trips;
import Tables.User;
import java.sql.*;
import java.util.ArrayList;

public class DBWorker implements databaseInterface {
    private static DBWorker db = null;
    private static final String url = "jdbc:mysql://localhost:3306/database2?serverTimezone=Europe/Moscow";
    private static final String user = "root";
    private static final String password = "root";
    private PreparedStatement preparedStatement;
    private Connection connection;
    private int userid = 0;
    private boolean flag = false;
    private int taxCount = 0;
    private static ArrayList<User> userInfo;
    private static ArrayList<Trips> List;

    public Connection getConnection() {
        return connection;
    }

    public DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection success!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static DBWorker getInstance() {
        if (db == null) {
            db = new DBWorker();
        }
        return db;
    }

    public boolean logIN(String login, String pass) {
        flag = false;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM user WHERE (login,password)= (?,md5(?))");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("id");
                flag = true;
            }
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean RegisterNewUser(String name, String surname, String email, String login, String pass, String phone) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO user(name, surname,phone, email, login, password) VALUES (?,?,?,?,?,md5(?))");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, login);
            preparedStatement.setString(6, pass);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public ArrayList<User> userProfileInfo(String login) {
        userInfo = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT user.name,user.surname,user.email,user.login,user.phone from user where user.login = (?)");
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userInfo.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return userInfo;
    }

    public ArrayList<Trips> Dhistory() throws SQLException {
        List = new ArrayList();
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT city, date, time, address, address_prib, place, price, car_brand FROM database2.trips WHERE userid ='" + userid + "'");
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");
                String city = rs.getString("city");
                String time = rs.getString("time");
                String address = rs.getString("address");
                String address_prib = rs.getString("address_prib");
                String place = rs.getString("place");
                String price = rs.getString("price");
                String car_brand = rs.getString("car_brand");
                List.add(new Trips(date, time, city, address, address_prib, place, car_brand, price));
            }
        } catch (Exception var3) {
            var3.getMessage();
        }
        return List;
    }

    public ArrayList<Trips> Phistory() throws SQLException {
        List = new ArrayList();
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT city, date, time, address, address_prib, place, price, car_brand FROM database2.histori_trips WHERE userid ='" + userid + "'");
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");
                String city = rs.getString("city");
                String time = rs.getString("time");
                String address = rs.getString("address");
                String address_prib = rs.getString("address_prib");
                String place = rs.getString("place");
                String price = rs.getString("price");
                String car_brand = rs.getString("car_brand");

                List.add(new Trips(date, time, city, address, address_prib, place, car_brand, price));
            }
        } catch (Exception var3) {
            var3.getMessage();
        }

        return List;
    }

    public boolean UpdateTrips(String tripsid, String date, String time, String city, String address, String address_prib, String place, String car_brand, String price, String driver) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO trips(trips_id,date,time,city,address,address_prib,place,car_brand, price,userid) VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, tripsid);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, address_prib);
            preparedStatement.setString(7, place);
            preparedStatement.setString(8, car_brand);
            preparedStatement.setString(9, price);
            preparedStatement.setString(10, driver);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean booking(Trips trips, Trips trips1) {
        try {
            int num = Integer.parseInt(trips1.getNumber_of_seats());
            this.preparedStatement = this.connection.prepareStatement("SELECT trips_id, date,time,city,address,address_prib,place,car_brand, price,userid FROM database2.trips WHERE address = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            this.flag = true;
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                String tripsid = String.valueOf(rs.getInt("trips_id"));
                String date = rs.getString("date");
                String city = rs.getString("city");
                String time = rs.getString("time");
                String address = rs.getString("address");
                String address_prib = rs.getString("address_prib");
                int place = Integer.parseInt(rs.getString("place"));
                String price = rs.getString("price");
                String car_brand = rs.getString("car_brand");
                String driverid = rs.getString("userid");
                int num_place = place - num;
                if (num_place >= 0) {
                    try {
                        preparedStatement = connection.prepareStatement("INSERT INTO histori_trips(userid,tripsid,date,time,city,address,address_prib,place,car_brand, price, driverid) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1, String.valueOf(userid));
                        preparedStatement.setString(2, tripsid);
                        preparedStatement.setString(3, date);
                        preparedStatement.setString(4, time);
                        preparedStatement.setString(5, city);
                        preparedStatement.setString(6, address);
                        preparedStatement.setString(7, address_prib);
                        preparedStatement.setString(8, String.valueOf(num));
                        preparedStatement.setString(9, car_brand);
                        preparedStatement.setString(10, price);
                        preparedStatement.setString(11, driverid);
                        preparedStatement.executeUpdate();
                        delete(trips);
                        UpdateTrips(tripsid, date, time, city, address, address_prib, String.valueOf(num_place), car_brand, price, driverid);
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return this.flag;
    }

    public boolean deletetrips(Trips trips) {
        try {
            this.flag = true;
            delete2(trips);
            delete(trips);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return this.flag;
    }

    public ArrayList<Trips> Pas_Date(Trips trips) {
        int i = 0;
        String[] mas = new String[20];
        List = new ArrayList<>();
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT userid FROM database2.histori_trips WHERE address = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                    String userid = rs.getString("userid");
                    mas[i] = userid;
                i = i + 1;
                }
            for ( i = 0; i < 20; i++) {
                System.out.println(mas[i]);
            }
            try {
                for ( i = 0; i < 20; i++) {
                    this.preparedStatement = this.connection.prepareStatement("SELECT user.name,user.surname,user.email,user.phone from user where user.id = '" + mas[i] + "'");
                    rs = this.preparedStatement.executeQuery();
                    while (rs.next()) {
                        List.add(new Trips(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                }
            } catch (Exception var3) {
                var3.getMessage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return List;
    }

    public ArrayList<User> date(Trips trips) {
        userInfo = new ArrayList<>();
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT driverid FROM database2.histori_trips WHERE address = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                String driverid = String.valueOf(rs.getInt("driverid"));
                preparedStatement = connection.prepareStatement("SELECT user.name,user.surname,user.email,user.phone from user where user.id = '" + driverid + "'");
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    userInfo.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userInfo;
    }

    public boolean deleteTax(Trips trips) {
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT place FROM database2.histori_trips WHERE address = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            this.flag = true;
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                int pl = rs.getInt("place");
                this.preparedStatement = this.connection.prepareStatement("SELECT trips_id, date,time,city,address,address_prib,place,car_brand, price,userid FROM database2.trips WHERE address = (?)");
                this.preparedStatement.setString(1, trips.getAddress());
                this.flag = true;
                rs = this.preparedStatement.executeQuery();
                while (rs.next()) {
                    String tripsid = String.valueOf(rs.getInt("trips_id"));
                    String date = rs.getString("date");
                    String city = rs.getString("city");
                    String time = rs.getString("time");
                    String address = rs.getString("address");
                    String address_prib = rs.getString("address_prib");
                    int place = Integer.parseInt(rs.getString("place"));
                    String price = rs.getString("price");
                    String car_brand = rs.getString("car_brand");
                    String driverid = rs.getString("userid");
                    int num_place = place + pl;
                    if (num_place >= 0) {
                        delete2(trips);
                        delete(trips);
                        UpdateTrips(tripsid, date, time, city, address, address_prib, String.valueOf(num_place), car_brand, price, driverid);
                        return true;
                    }
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return this.flag;
    }


    public boolean delete2(Trips trips) {
        try {
            this.preparedStatement = this.connection.prepareStatement("DELETE FROM database2.histori_trips WHERE address  = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            this.preparedStatement.executeUpdate();
            this.flag = true;
        } catch (Exception var3) {
            var3.printStackTrace();
            this.flag = false;
        }
        return this.flag;
    }

    public boolean delete(Trips trips) {
        try {
            this.preparedStatement = this.connection.prepareStatement("DELETE FROM database2.trips WHERE address = (?)");
            this.preparedStatement.setString(1, trips.getAddress());
            this.preparedStatement.executeUpdate();
            this.flag = true;
        } catch (Exception var3) {
            var3.printStackTrace();
            this.flag = false;
        }
        return this.flag;
    }

    public boolean deleteRecord(String login) {
        flag = false;
        try {
            preparedStatement = connection.prepareStatement("DELETE from user WHERE login=(?)");
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.getMessage();
        }
        return flag;
    }

    public boolean RegisterNewTrips(String date, String time, String city, String address, String address_prib, String place, String car_brand, String price) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO trips(date,time,city,address,address_prib,place,car_brand, price,userid) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, city);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, address_prib);
            preparedStatement.setString(6, place);
            preparedStatement.setString(7, car_brand);
            preparedStatement.setString(8, price);
            preparedStatement.setString(9, String.valueOf(userid));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Trips> showHistory(String cit, String dat) {
        List = new ArrayList();
        try {
            this.preparedStatement = this.connection.prepareStatement("SELECT city, date, time, address, address_prib, place, price, car_brand FROM database2.trips WHERE city ='" + cit + "' and date='" + dat + "'");
            ResultSet rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                int placee = Integer.parseInt(rs.getString("place"));
                if (placee > 0) {
                    String date = rs.getString("date");
                    String city = rs.getString("city");
                    String time = rs.getString("time");
                    String address = rs.getString("address");
                    String address_prib = rs.getString("address_prib");
                    String place = rs.getString("place");
                    String price = rs.getString("price");
                    String car_brand = rs.getString("car_brand");
                    List.add(new Trips(date, time, city, address, address_prib, place, car_brand, price));
                }
            }
        } catch (Exception var3) {
            var3.getMessage();
        }

        return List;
    }
}
