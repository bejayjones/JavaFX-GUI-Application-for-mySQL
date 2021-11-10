package com.example.schedulingapp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class addCustomerController {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField zipcodeTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<?> stateCombo;

    @FXML
    private ComboBox<?> countryCombo;

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }

    @FXML
    void closeButtonClicked(ActionEvent event) {

    }

}
