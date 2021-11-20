package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBfirstLevelDivison;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Customers;
import com.example.schedulingapp.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {
    /**
     * textfield used to gather last name from user
     */
    @FXML
    private TextField lastNameTextField;
    /**
     * textfield used to gather first name from user
     */
    @FXML
    private TextField firstNameTextField;
    /**
     * textfield used to gather address from user
     */
    @FXML
    private TextField addressTextField;
    /**
     * textfield used to gather zipcode from user
     */
    @FXML
    private TextField zipcodeTextField;
    /**
     * textfield used to gather phone number from user
     */
    @FXML
    private TextField phoneNumberTextField;
    /**
     * combo box that lets user select a state
     */
    @FXML
    private ComboBox<String> stateCombo;
    /**
     * combo box that lets user select a country
     */
    @FXML
    private ComboBox<String> countryCombo;

    @FXML
    private TextField idTextField;

    /**
     * checks for input validation, then updates the select customer with the input data
     * in the mysql database
     * @param event
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {
        String customerName = firstNameTextField.getText() + " " + lastNameTextField.getText();
        String customerPostalCode = zipcodeTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPhone = phoneNumberTextField.getText();
        int customerId = Integer.parseInt(idTextField.getText());
        try {
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("UPDATE client_schedule.customers" +
                    " SET Customer_Name = ?, Postal_Code = ?, Address = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?");
            ps.setString(1, customerName);
            ps.setString(2, customerPostalCode);
            ps.setString(3, customerAddress);
            ps.setString(4, customerPhone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);
            ps.executeUpdate();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            goHome();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Confirms that the user wants to cancel the addition process and then returns user back to
     * home view
     * @param event
     * @throws IOException
     */
    @FXML
    void closeButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel updating this Customer?");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
            try {
                goHome();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * returns the user back the home view
     * @throws IOException
     */
    void goHome() throws IOException {
        Stage stage = new Stage();
        Locale userLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.home", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("home.fxml"), bundle);
        Scene scene = new Scene(root);
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Customer / Appointment");
        } else if (userLocale.getLanguage().equals("fr")) {
            stage.setTitle("Client / Rendevouz");
        }
        stage.setScene(scene);
        stage.show();
    }
    private ObservableList<firstLevelDivision> allDivisionData = DBfirstLevelDivison.getAllFirstLevelDivision();
    private ObservableList<String> firstLevelList = DBfirstLevelDivison.getAllDivisions();
    private ObservableList<String> usStates = DBfirstLevelDivison.getStates();
    private ObservableList<String> ukProvinces = DBfirstLevelDivison.getUKProvinces();
    private ObservableList<String> canadaProvinces = DBfirstLevelDivison.getCanadaProvinces();
    private ObservableList<String> countries = FXCollections.observableArrayList();
    private int divisionId;

    /**
     * populates the state combo box based on the selection of the country combo box
     * @param event
     */
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

    /**
     * populates the country combo box based on the selection of the state combo box
     * @param event
     */
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

    /**
     * initializes the update customer screen by populating the textfields and combo boxes with the data
     * from the selected customer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateCombo.setItems(firstLevelList);
        countries.add("Canada");
        countries.add("United Kingdom");
        countries.add("United States");
        countryCombo.setItems(countries);
        Customers selectedCustomer = homeController.getSelectedCustomer();
        String[] name = selectedCustomer.getCustomerName().split(" ");
        idTextField.setText(String.valueOf(selectedCustomer.getCustomerId()));
        firstNameTextField.setText(String.valueOf(name[0]));
        lastNameTextField.setText(String.valueOf(name[1]));
        zipcodeTextField.setText(String.valueOf(selectedCustomer.getCustomerPostalCode()));
        addressTextField.setText(String.valueOf(selectedCustomer.getCustomerAddress()));
        phoneNumberTextField.setText(String.valueOf(selectedCustomer.getCustomerPhone()));
        divisionId = selectedCustomer.getCustomerDivisonId();
        for(firstLevelDivision state : allDivisionData){
            if(selectedCustomer.getCustomerDivisonId() == state.getDivisionId()){
                stateCombo.setValue(state.getDivisionName());
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
}
