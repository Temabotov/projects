package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.Client;
import Tables.Trips;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableController implements Initializable {
    @FXML
    private TableView<Trips> List;
    @FXML
    private TableColumn<Trips, String> Date;
    @FXML
    private TableColumn<Trips, String> Time;
    @FXML
    private TableColumn<Trips, String> City;
    @FXML
    private TableColumn<Trips, String> Address;
    @FXML
    private TableColumn<Trips, String> Address_prib;
    @FXML
    private TableColumn<Trips, String> Place;
    @FXML
    private TableColumn<Trips, String> Car_brand;
    @FXML
    private TableColumn<Trips, String> Price;
    public TextField number_of_seats;

    Trips taxChose;
    private Client connection = Client.getInstance();

    private void selectedTax(Trips tax) {
        if (tax != null) {
            this.taxChose = tax;
        }
    }

    public void booking(ActionEvent actionEvent) {
        Alert alert;
        if (number_of_seats.getText().trim().equals("")) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nВЫ заполнили не все поля");
            alert.showAndWait();
        } else {
            Trips newTrips = new Trips(this.number_of_seats.getText());
            this.connection.sendMessage("MoneyTax");
            this.connection.sendObject(this.taxChose);
            this.connection.sendObject1(newTrips);
        if (this.connection.readMessage().equals("Good")) {
            System.out.println("booking");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("В этой поездке недостаточное количество мест.");
            alert.showAndWait();
            System.out.println("Error");
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Trips> dataList = (ArrayList)this.connection.readObject();
        ObservableList<Trips> data = FXCollections.observableArrayList(dataList);
        Date.setCellValueFactory(new PropertyValueFactory("Date"));
        Time.setCellValueFactory(new PropertyValueFactory("Time"));
        City.setCellValueFactory(new PropertyValueFactory("City"));
        Address_prib.setCellValueFactory(new PropertyValueFactory("Address_prib"));
        Address.setCellValueFactory(new PropertyValueFactory("Address"));
        Price.setCellValueFactory(new PropertyValueFactory("Price"));
        Place.setCellValueFactory(new PropertyValueFactory("Place"));
        Car_brand.setCellValueFactory(new PropertyValueFactory("Car_brand"));
        List.setItems(data);
        this.List.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedTax(newValue);
        });
    }
}
