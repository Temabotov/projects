package controllers;

import Client.Client;
import Tables.User;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ProfileController implements Initializable {
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label email;
    @FXML
    private Label phone;

    private Client connection = Client.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<User> user = (ArrayList)this.connection.readObject();
        Iterator var4 = user.iterator();
        while(var4.hasNext()) {
            User users = (User)var4.next();
            this.firstName.setText(users.getUsername());
            this.lastName.setText(users.getUserSurname());
            this.email.setText(users.getEmail());
            this.phone.setText(users.getPhone());
        }

    }
}