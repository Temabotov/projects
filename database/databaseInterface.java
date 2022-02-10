package database;

import Tables.User;
import java.util.ArrayList;

public interface databaseInterface {
    boolean logIN(String login, String pass);
    boolean RegisterNewUser(String name,String surname,String email,String login,String pass, String phone);
    boolean RegisterNewTrips(String date, String time, String city, String address, String address_prib, String place, String car_brand, String price);
    boolean deleteRecord(String login);
    ArrayList<User> userProfileInfo(String login);
}
