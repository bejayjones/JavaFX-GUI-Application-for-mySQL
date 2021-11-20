package com.example.schedulingapp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;

/**
 * Login controller class that controls the logic for the login view
 */
public class loginController implements Initializable {
    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label loginLabel;
    /**
     * textfield used to collect username
     */
    @FXML
    private TextField userNameField;

    @FXML
    private Label passWordLabel;
    /**
     * textfield used to collect password
     */
    @FXML
    private PasswordField passWordField;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button loginButton;
    /**
     * label to display users timezone
     */
    @FXML
    private Label loginTimeZoneLabel;

    /**
     * When button is clicked, check for correct username and password
     * if correct, write to login_activity.txt, display home view
     * if incorrect, write to login_activity.txt, throw wrong username/password alert
     * @param event
     * @throws IOException
     */
    public void loginButtonOnAction(ActionEvent event) throws IOException {
        String message = "Wrong username or password";
        String alertTitle = "Login failed";
        Locale userLocale = Locale.getDefault();
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter myWriter = new PrintWriter(fw);

            if (userNameField.getText().equals("test") && passWordField.getText().equals("test")) {
                Stage stage = new Stage();
                ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.home", userLocale);
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("home.fxml"), bundle);
                Scene scene = new Scene(root);
                myWriter.println("LOGIN SUCCESSFUL" + LocalDateTime.now());
                myWriter.close();
                if (userLocale.getLanguage().equals("en")) {
                    stage.setTitle("Customer / Appointment");

                } else if (userLocale.getLanguage().equals("fr")) {
                    stage.setTitle("Client / Rendevouz");
                }
                stage.setScene(scene);
                stage.show();
                //hide the login window
                ((Node) (event.getSource())).getScene().getWindow().hide();
                homeController.checkForAppointment();
            } else {
                myWriter.println("LOGIN UNSUCCESSFUL" + LocalDateTime.now());
                myWriter.close();
                if (userLocale.getLanguage().equals("en")) {

                } else if (userLocale.getLanguage().equals("fr")) {
                    message = "Nom d'utilisateur ou mot de passe erroné";
                    alertTitle = "échec de la connexion";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(alertTitle);
                alert.setHeaderText(message);
                alert.show();
            }

    }

    /**
     * initialize the view by displaying users timezone
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginTimeZoneLabel.setText(ZoneId.systemDefault().toString());
    }
}
