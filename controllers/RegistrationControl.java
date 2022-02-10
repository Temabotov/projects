package controllers;

import Client.Client;
import Tables.EmailValidator;
import Tables.PhoneValidator;
import Tables.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationControl {
    public TextField name;
    public TextField surname;
    public TextField email;
    public TextField login;
    public TextField phone;
    public PasswordField password;
    private Client connection = Client.getInstance();
    private EmailValidator emailValidator = new EmailValidator();
    private PhoneValidator phoneValidator = new PhoneValidator();

    public void MakeRegistration(ActionEvent actionEvent) throws IOException {
        User newUser = new User(this.name.getText(), this.surname.getText(), this.email.getText(), this.login.getText(), this.password.getText(),this.phone.getText());
        Alert alert;
        if (name.getText().trim().equals("") || surname.getText().trim().equals("") || email.getText().trim().equals("") || login.getText().trim().equals("") || password.getText().trim().equals("") || phone.getText().trim().equals("")) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nВЫ заполнили не все поля");
            alert.showAndWait();
        } else {
            if ((this.emailValidator.validate(this.email.getText())) & ((this.phoneValidator.validate(this.phone.getText())))) {
                this.connection.sendMessage("SignIn");
                this.connection.sendObject(newUser);
                if (this.connection.readMessage().equals("Good")) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Вы успешно прошли регистрацию");
                    alert.setContentText(this.name.getText() + " " + this.surname.getText() + ", теперь Вы можете войти,\nиспользуя свой логин и пароль");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.hide();
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText("Заполните все поля");
                    alert.setContentText("Произошла ошибка!\nВЫ ввели некоректрый E-mail\nили такой логин уже занят");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Заполните все поля");
                alert.setContentText("Произошла ошибка!\nНаверное, Вы ввели некоректрый E-mail\nили не корректный номер телефона");
                alert.showAndWait();
            }
        }
    }
}
