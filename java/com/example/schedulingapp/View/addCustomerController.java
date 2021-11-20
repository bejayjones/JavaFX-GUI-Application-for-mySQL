package com.example.schedulingapp.View;
import com.example.schedulingapp.DBAccess.DBfirstLevelDivison;
import com.example.schedulingapp.Database.DBUtil;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * addCustomerController responsible for controlling the logic surrounding the process of adding a new customer
 */
public class addCustomerController implements Initializable {
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

    private ObservableList<firstLevelDivision> allDivisionData = DBfirstLevelDivison.getAllFirstLevelDivision();
    private ObservableList<String> firstLevelList = DBfirstLevelDivison.getAllDivisions();
    private ObservableList<String> usStates = DBfirstLevelDivison.getStates();
    private ObservableList<String> ukProvinces = DBfirstLevelDivison.getUKProvinces();
    private ObservableList<String> canadaProvinces = DBfirstLevelDivison.getCanadaProvinces();
    private ObservableList<String> countries = FXCollections.observableArrayList();
    private int divisionId;

    /**
     * set the items of the state combo box based on the selection of the
     * country combo box
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
     * sets the selection of the country combo box based on the selection of
     * the state combo box
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
     * checks for input validation then passes the user input into a new customer and adds the customer
     * to the mysql database
     * @param event
     * @throws IOException
     */
    @FXML
    void saveButtonClicked(ActionEvent event) throws IOException {
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
            goHome();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * takes the user back to the home view
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

    /**
     * Confirms that the user wants to cancel the addition process and then returns user back to
     * home view
     * @param event
     * @throws IOException
     */
    @FXML
    void closeButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel adding a new Customer?");
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
     * initializes the view by setting teh country and state combo boxes
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
    }
}
