package controllers;

import Client.Client;
import Tables.User;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class userProfileController implements Initializable {
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private Label phone;

    private Client connection = Client.getInstance();

    public userProfileController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.connection.sendMessage("MakeProfile");
        ArrayList<User> user = (ArrayList)this.connection.readObject();
        Iterator var4 = user.iterator();
        while(var4.hasNext()) {
            User users = (User)var4.next();
            this.firstName.setText(users.getUsername());
            this.lastName.setText(users.getUserSurname());
            this.email.setText(users.getEmail());
            this.username.setText(users.getLogin());
            this.phone.setText(users.getPhone());
        }
    }

    @FXML
    private void changeColor(ActionEvent event) {
        Stage stage3;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление записи");
                alert.setHeaderText("Удаление учетной записи пользователя");
                alert.setContentText("Вы уверены, что хотите удалить\nВашу учетную запись?");
                Stage stage2 = (Stage)alert.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(this.getClass().getResource("/icons/City.png").toString()));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    this.connection.sendMessage("DeleteRecords");
                    if (this.connection.readMessage().equals("Good")) {
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        stage3 = (Stage)infoAlert.getDialogPane().getScene().getWindow();
                        stage3.getIcons().add(new Image(this.getClass().getResource("/icons/City.png").toString()));
                        infoAlert.setTitle("Уведомление");
                        infoAlert.setHeaderText((String)null);
                        infoAlert.setContentText("Операция прошла успешно! Ваши данные удалены из системы.\nПосле закрытия окна, приложение будет закрыто.");
                        infoAlert.showAndWait();
                    }

                    System.exit(0);
                }
        }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
}
