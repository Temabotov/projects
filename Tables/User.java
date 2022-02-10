package Tables;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String userSurname;
    private String email;
    private String login;
    private String password;
    private String phone;


    public User(String username, String userSurname, String email, String login, String password, String phone) {
        this.username =username;
        this.userSurname =userSurname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String userSurname, String email, String login, String phone) {
        this.username = username;
        this.userSurname = userSurname;
        this.email = email;
        this.login = login;
        this.phone = phone;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String username, String userSurname, String email, String phone) {
        this.username = username;
        this.userSurname = userSurname;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
