package com.example.schedulingapp;

import com.example.schedulingapp.Database.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");

        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.login", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/login.fxml"), bundle);
        Scene scene = new Scene(root);
        System.out.println(userLocale.getLanguage());
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Login");
        }
        else if(userLocale.getLanguage().equals("fr")){
            stage.setTitle("Connexion");
        }
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        DBUtil.openConnection();
        launch();
    }

}