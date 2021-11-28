package com.example.schedulingapp.View;
import com.example.schedulingapp.DBAccess.DBAppointments;
import com.example.schedulingapp.DBAccess.DBCustomers;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Appointments;
import com.example.schedulingapp.model.Customers;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * homeController classes responsible for the logic of displaying tables and user options
 */
public class homeController implements Initializable{

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Customers, Integer> custID;

    @FXML
    private TableColumn<Customers, String> custName;

    @FXML
    private TableColumn<Customers, String> custAddress;

    @FXML
    private TableColumn<Customers, String> custZipCode;

    @FXML
    private TableColumn<Customers, String> custPhoneNumber;

    @FXML
    private TableColumn<Appointments, Integer> apptID;

    @FXML
    private TableColumn<Appointments, String> apptTitle;

    @FXML
    private TableColumn<Appointments, String> apptType;

    @FXML
    private TableColumn<Appointments, String> apptStart;

    @FXML
    private TableColumn<Appointments, String> apptEnd;

    @FXML
    private TableColumn<Appointments, String> apptDescription;

    @FXML
    private TableColumn<Appointments, Integer> apptcustomerId;

    @FXML
    private TableColumn<Appointments, Integer> apptUserId;

    @FXML
    private TableColumn<Appointments, Integer> apptContactId;

    /**
     * method used to return the list of appointments
     * @return appointmentList
     */
    public static ObservableList<Appointments> getAppointmentList() {
        return appointmentList;
    }

    /**
     * opens the add appointment view
     * @param event
     * @throws IOException
     */
    @FXML
    void addApptButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.appointment", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("addAppointment.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Add Appointment");
        }
        else if(userLocale.getLanguage().equals("fr")){
            stage.setTitle("ajouter un rendez-vous");
        }
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    /**
     * opens the add customer view
     * @param event
     * @throws IOException
     */
    @FXML
    void addCustButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.customer", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("addCustomer.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Add Customer");
        }
        else if(userLocale.getLanguage().equals("fr")){
            stage.setTitle("ajouter un client");
        }
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    /**
     * confirms that the user wants to delete the selected appointment
     * LAMBDA EXPRESSION: I used a lambda expression in this method for my alerts so that it passes the user response
     * into a lambda expression to decide whether to delete the appointment or not
     * @param event
     */
    void deleteApptButtonClicked(ActionEvent event) {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> deleteAppt());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You must select an appointment first");
            alert.show();
        }
    }

    /**
     * deletes the selected appointment
     */
    void deleteAppt(){
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        String aptId = String.valueOf(selectedAppointment.getAppointmentId());
        String aptType = selectedAppointment.getAppointmentType();
        String message = "Appointment: " + aptId + " Type: " + aptType + " has been cancelled";
        try{
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?");
            ps.setString(1, String.valueOf(selectedAppointment.getAppointmentId()));
            ps.executeUpdate();
            setLists();
            setTables();
        } catch(SQLException e){
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    /**
     * opens the update appointment view and sets the selected appointment so that the next view can access it
     * @param event
     * @throws IOException
     */
    @FXML
    void updateApptButtonClicked(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        Locale userLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.appointment", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("updateAppointment.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Update Appointment");
        }
        else if(userLocale.getLanguage().equals("fr")){
            stage.setTitle("rendez-vous de mise à jour");
        }
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * opens the update Customer view and sets the selected customer so the view can access it
     * @param event
     * @throws IOException
     */
    @FXML
    void updateCustButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.customer", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("updateCustomer.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Update Customer");
        }
        else if(userLocale.getLanguage().equals("fr")){
            stage.setTitle("mettre à jour le client");
        }
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * confirms that the user wants to delete the selected customer
     * LAMBDA EXPRESSION: I used a lambda expression in this method for my alerts so that it passes the user response
     * into a lambda expression to decide whether to delete the customer or not
     * @param event
     */
    @FXML
    void deleteCustButtonClicked(ActionEvent event){
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> deleteCustomer());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You must select an customer first");
            alert.show();
        }
    }

    /**
     * deletes the selected customer
     */
    void deleteCustomer(){
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("DELETE FROM client_schedule.appointments WHERE Customer_ID = ?");
            ps.setString(1, String.valueOf(selectedCustomer.getCustomerId()));
            ps.executeUpdate();
        } catch(SQLException e){

        }

        try {
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("DELETE FROM client_schedule.customers WHERE Customer_ID = ?");
            ps.setString(1, String.valueOf(selectedCustomer.getCustomerId()));
            ps.executeUpdate();
            setLists();
            setTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes the appointment table to display all the appointments in the upcoming 30 days
     * @param event
     */
    @FXML
    void monthlyRadioButtonSelected(ActionEvent event){
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();
        for(Appointments A : appointmentList){

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(start.isAfter(now) && start.isBefore(now.plusHours(730))) {
                monthlyAppointments.add(A);
            }
        }
        appointmentTable.setItems(null);
        appointmentTable.setItems(monthlyAppointments);
    }

    /**
     * changes the appointment table to display all the appointments in the upcoming 7 days
     * @param event
     */
    @FXML
    void weeklyRadioButtonSelected(ActionEvent event){
        ObservableList<Appointments> weeklyAppointment = FXCollections.observableArrayList();
        for(Appointments A : appointmentList){

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(start.isAfter(now) && start.isBefore(now.plusHours(168))) {
                weeklyAppointment.add(A);
            }
        }
        appointmentTable.setItems(null);
        appointmentTable.setItems(weeklyAppointment);
    }

    /**
     * resets the appointment table so that all appointments are displayed
     * @param event
     */
    @FXML
    void allRadioButtonSelected(ActionEvent event){
        setTables();
    }

    /**
     * refreshes the appointment lists
     */
    public static void setLists(){
        customerList = null;
        appointmentList = null;
        customerList = DBCustomers.getAllCustomers();
        appointmentList = DBAppointments.getAllAppointments();

    }

    /**
     * refreshes the tables
     */
    public void setTables(){
        appointmentTable.setItems(appointmentList);
        customerTable.setItems(customerList);
    }
    private static Customers selectedCustomer;
    private static Appointments selectedAppointment;

    /**
     * @return selectedCustomer
     */
    public static Customers getSelectedCustomer(){
        return selectedCustomer;
    }

    /**
     *
     * @return selectedAppointment
     */
    public static Appointments getSelectedAppointment(){
        return selectedAppointment;
    }

    /**
     * checks to see if there is an appointment within the next 15 minutes
     */
    public static void checkForAppointment(){
        LocalDateTime now = LocalDateTime.now();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int check = 0;
        for(Appointments A : appointmentList){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime aStartTime = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            if(aStartTime.isAfter(now) && aStartTime.isBefore(now.plusMinutes(15))){
                alert.setHeaderText("There is an appointment within 15 minutes!");
                alert.setContentText("Appointment ID: " + A.getAppointmentId() + "\n Time: " + A.getAppointmentStartDate());
                alert.showAndWait();
                check++;
            }
            else{

            }
        }
        if(check == 0) {
            alert.setHeaderText("No upcoming appointments");
            alert.showAndWait();
        }
    }

    /**
     * opens the reports view
     * @throws IOException
     */
    @FXML
    void reportsButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("reports.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    private static ObservableList customerList = FXCollections.observableArrayList();
    private static ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

    /**
     * initializes the view so that the tables are populated with the corresponding data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));


        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptcustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        setLists();
        setTables();
    }
}

