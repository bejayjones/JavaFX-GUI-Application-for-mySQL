package com.example.schedulingapp.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class updateAppointmentController {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button updateButton;

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
    void closeButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }

}
