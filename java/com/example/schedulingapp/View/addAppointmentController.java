package com.example.schedulingapp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private DatePicker dateWidget;

    @FXML
    private ComboBox<?> startMinuteCombo;

    @FXML
    private ComboBox<?> endMinuteCombo;

    @FXML
    private ComboBox<?> endHourCombo;

    @FXML
    private ComboBox<?> startHourCombo;

    @FXML
    private ComboBox<?> startMeridiemCombo;

    @FXML
    private ComboBox<?> endMeridiemCombo;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }

    @FXML
    void closeButtonClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
