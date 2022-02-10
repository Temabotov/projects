package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.*;
import java.net.Socket;

public class Main extends Application {

    private static Socket clientSock;
    private static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.getIcons().add(new Image("icons/City.png"));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CityCar");
        primaryStage.setScene(new Scene(root, 628.0, 387.0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
