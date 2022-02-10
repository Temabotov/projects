package controllers;

import Client.Client;
import Tables.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller implements Initializable {
    public TextField city;
    public TextField address;
    public TextField address_prib;
    public DatePicker date;
    public TextField time;
    public TextField place;
    public TextField price;
    public TextField car_brand;
    public TextField filtr_city;
    public DatePicker filtr_date;

    private Client connection = Client.getInstance();
    private TimeValidator timeValidator = new TimeValidator();
    private Validator validator = new Validator();


    public void showUserProfile(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            stage.getIcons().add(new Image("icons/City.png"));
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/userProfile.fxml"));
            stage.setTitle("Профиль пользователя");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    public void MakePublication(ActionEvent actionEvent) throws IOException {
        String dat = String.valueOf((LocalDate)this.date.getValue());
        Trips newTrips = new Trips(dat, this.time.getText(), this.city.getText(), this.address.getText(), this.address_prib.getText(), this.place.getText(), this.car_brand.getText(), this.price.getText());
        Alert alert;
        if (city.getText().trim().equals("")|| dat.trim().equals("") || address.getText().trim().equals("") || address_prib.getText().trim().equals("") || time.getText().trim().equals("") || place.getText().trim().equals("") || price.getText().trim().equals("") || car_brand.getText().trim().equals("")) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nВЫ заполнили не все поля");
            alert.showAndWait();
        } else {
            if ((this.timeValidator.validate(this.time.getText())) & (this.validator.validate(this.address.getText())) & (this.validator.validate(this.address_prib.getText()))) {
                String place = this.place.getText();
                String price = this.price.getText();
                if (isNumeric(place,price)) {
                    this.connection.sendMessage("GoodsTax");
                    this.connection.sendObject(newTrips);
                    if (this.connection.readMessage().equals("Good")) {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Регистрация");
                        alert.setHeaderText("Вы успешно прошли регистрацию");
                        alert.setContentText("Поездка зарегистрирована!");
                        alert.showAndWait();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.hide();
                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image("icons/City.png"));
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
                        stage.setTitle("CityCar");
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();
                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Ошибка!");
                        alert.setContentText("Что-то пошло не так");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Ошибка!");
                    alert.setContentText("Произошла ошибка!\nНаверное, Вы ввели некоректро количество мест\nили не корректная цена");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Заполните все поля");
                alert.setContentText("Произошла ошибка!\nНаверное, Вы ввели некоректро время\nили не корректный адресс");
                alert.showAndWait();
            }
        }
    }

    public void Search() throws IOException {
        Alert alert;
        String dat = String.valueOf((LocalDate) this.filtr_date.getValue());
        Trips newTrips = new Trips(this.filtr_city.getText(), dat);
        if (filtr_city.getText().trim().equals("") || dat.trim().equals("")) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nВЫ заполнили не все поля");
            alert.showAndWait();
        } else {
            this.connection.sendMessage("ShowHistory");
            this.connection.sendObject(newTrips);
            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("icons/City.png"));
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/Table.fxml"));
                stage.setTitle("Поиск поездок");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception var11) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка!");
                alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
                alert.showAndWait();
            }
        }
    }

    public void driver_history (ActionEvent actionEvent) throws IOException {
        Alert alert;
        this.connection.sendMessage("FindTax");
        try {
            Stage stage = new Stage();
            stage.getIcons().add(new Image("icons/City.png"));
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/driver_history.fxml"));
            stage.setTitle("История поездок");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception var11) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
        }
    }

    public void passenger_history (ActionEvent actionEvent) throws IOException {
        Alert alert;
            this.connection.sendMessage("Shutdown");
            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("icons/City.png"));
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/passenger_history.fxml"));
                stage.setTitle("История поездок");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception var11) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка!");
                alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
                alert.showAndWait();
            }
    }

    public static boolean isNumeric(String str, String str1) {
        try {
            double var1 = Double.parseDouble(str);
            double var2 = Double.parseDouble(str1);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
    }
}

