package com.example.schedulingapp.View;
import com.example.schedulingapp.DBAccess.DBfirstLevelDivison;
import com.example.schedulingapp.model.Customers;
import com.example.schedulingapp.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

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
    private ComboBox<String> stateCombo;

    @FXML
    private ComboBox<String> countryCombo;

    private ObservableList<firstLevelDivision> allDivisionData = DBfirstLevelDivison.getAllFirstLevelDivision();
    private ObservableList<String> firstLevelList = DBfirstLevelDivison.getAllDivisions();
    private ObservableList<String> usStates = DBfirstLevelDivison.getStates();
    private ObservableList<String> ukProvinces = DBfirstLevelDivison.getUKProvinces();
    private ObservableList<String> canadaProvinces = DBfirstLevelDivison.getCanadaProvinces();
    private ObservableList<String> countries = FXCollections.observableArrayList();
    private int divisionId;

    @FXML
    void countrySelected(ActionEvent event){
        if(countryCombo.getValue() == "Canada"){
            stateCombo.setItems(canadaProvinces);
        }
        if(countryCombo.getValue() == "United Kingdom"){
            stateCombo.setItems(ukProvinces);
        }
        if(countryCombo.getValue() == "United States"){
            stateCombo.setItems(usStates);
        }
    }
    @FXML
    void stateSelected(ActionEvent event){

        for(firstLevelDivision state : allDivisionData){

            if(stateCombo.getValue().equals(state.getDivisionName())){
                divisionId = state.getDivisionId();
                switch(state.getCountryId()){
                    case 1:
                        countryCombo.setValue("United States");
                        break;
                    case 2:
                        countryCombo.setValue("United Kingdom");
                        break;
                    case 3:
                        countryCombo.setValue("Canada");
                        break;
                }
                break;
            }
        }

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {
        int customerId = 0;
        String customerName = firstNameTextField.getText() + " " + lastNameTextField.getText();
        String customerPostalCode = zipcodeTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPhone = phoneNumberTextField.getText();
        Customers C = new Customers(customerId, customerName, customerPostalCode, customerAddress,
                customerPhone, divisionId);
        homeController.addCustomer(C);
    }

    @FXML
    void closeButtonClicked(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateCombo.setItems(firstLevelList);
        countries.add("Canada");
        countries.add("United Kingdom");
        countries.add("United States");
        countryCombo.setItems(countries);
    }
}
