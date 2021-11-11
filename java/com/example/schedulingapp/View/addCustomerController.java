package com.example.schedulingapp.View;
import com.example.schedulingapp.DBAccess.DBfirstLevelDivison;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Customers;
import com.example.schedulingapp.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String customerName = firstNameTextField.getText() + " " + lastNameTextField.getText();
        String customerPostalCode = zipcodeTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPhone = phoneNumberTextField.getText();
        try {
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("INSERT INTO client_schedule.customers" +
                    "(Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    " VALUES(?, ?, ?, ?, ?)");
            ps.setString(1, customerName);
            ps.setString(2, customerPostalCode);
            ps.setString(3, customerAddress);
            ps.setString(4, customerPhone);
            ps.setInt(5, divisionId);
            ps.executeUpdate();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
