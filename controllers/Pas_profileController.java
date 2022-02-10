package controllers;

import Client.Client;
import Tables.Trips;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Pas_profileController implements Initializable {
    @FXML
    private TableView<Trips> List;
    @FXML
    private TableColumn<Trips, String> name;
    @FXML
    private TableColumn<Trips, String> surname;
    @FXML
    private TableColumn<Trips, String> email;
    @FXML
    private TableColumn<Trips, String> phone;

    private Client connection = Client.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Trips> dataList = (ArrayList)this.connection.readObject();
        ObservableList<Trips> data = FXCollections.observableArrayList(dataList);
        name.setCellValueFactory(new PropertyValueFactory("name"));
        surname.setCellValueFactory(new PropertyValueFactory("surname"));
        email.setCellValueFactory(new PropertyValueFactory("email"));
        phone.setCellValueFactory(new PropertyValueFactory("phone"));
        List.setItems(data);
    }
}
