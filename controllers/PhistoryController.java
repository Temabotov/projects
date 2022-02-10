package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PhistoryController implements Initializable {
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

    Trips taxChose;
    private Client connection = Client.getInstance();

    private void selectedTax(Trips tax) {
        if (tax != null) {
            this.taxChose = tax;
        }
    }

    public void deleteSelectedTax(ActionEvent actionEvent) {
        this.connection.sendMessage("DeleteTax");
        this.connection.sendObject(this.taxChose);
        if (this.connection.readMessage().equals("Good")) {
            System.out.println("deleted");
        } else {
            System.out.println("Error");
        }
    }

    public void data(ActionEvent actionEvent) {
        Alert alert;
        this.connection.sendMessage("Data");
        this.connection.sendObject(this.taxChose);
        try {
            Stage stage = new Stage();
            stage.getIcons().add(new Image("icons/City.png"));
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/Profile.fxml"));
            stage.setTitle("Профиль");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception var11) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
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
