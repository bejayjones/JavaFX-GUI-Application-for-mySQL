package com.example.schedulingapp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField userNameField;

    @FXML
    private Label passWordLabel;

    @FXML
    private PasswordField passWordField;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginTimeZoneLabel;

    public void loginButtonOnAction(ActionEvent event) throws IOException {
        String userName = "test";
        String passWord = "test";
            if (userNameField.getText().equals("test") && passWordField.getText().equals("test")) {
                Stage stage = new Stage();
                Locale userLocale = Locale.getDefault();
                Locale localeEN = new Locale("en_us");
                Locale localeFR = new Locale("fr_fr");

                ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.home", userLocale);
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("home.fxml"), bundle);
                Scene scene = new Scene(root);
                System.out.println(userLocale.getLanguage());
                if (userLocale.getLanguage().equals("en")) {
                    stage.setTitle("Customer / Appointment");
                } else if (userLocale.getLanguage().equals("fr")) {
                    stage.setTitle("Client / Rendevouz");
                }
                stage.setScene(scene);
                stage.show();
                //hide the login window
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                System.out.println("wrong username/password");
            }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginTimeZoneLabel.setText(ZoneId.systemDefault().toString());
    }
}
