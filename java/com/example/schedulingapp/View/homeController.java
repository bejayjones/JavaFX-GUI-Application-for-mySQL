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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

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

    @FXML
    private Label appointmentsLabel;

    @FXML
    private Label customersLabel;

    @FXML
    private Button addCustButton;

    @FXML
    private Button updateCustButton;

    @FXML
    private Button addApptButton;

    @FXML
    private Button updateApptButton;

    @FXML
    private Button deleteApptButton;

    @FXML
    private RadioButton monthlyRadioButton;

    @FXML
    private ToggleGroup schedule;

    @FXML
    private RadioButton weeklyRadioButton;

    @FXML
    private RadioButton allRadioButton;

    public static ObservableList<Appointments> getAppointmentList() {
        return appointmentList;
    }

    @FXML
    void addApptButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.appointment", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("addAppointment.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        System.out.println(userLocale.getLanguage());
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


    @FXML
    void addCustButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.customer", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("addCustomer.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        System.out.println(userLocale.getLanguage());
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
    void deleteApptButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> deleteAppt());
    }
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

    @FXML
    void updateApptButtonClicked(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.appointment", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("updateAppointment.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        System.out.println(userLocale.getLanguage());
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

    @FXML
    void updateCustButtonClicked(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.customer", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("updateCustomer.fxml"), bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        System.out.println(userLocale.getLanguage());
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
    @FXML
    void deleteCustButtonClicked(ActionEvent event){
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
    @FXML
    void monthlyRadioButtonSelected(ActionEvent event){
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();
        for(Appointments A : appointmentList){

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(start.isAfter(now) && start.isBefore(now.plusHours(730))) {
                monthlyAppointments.add(A);
                System.out.println("Added");
                System.out.println(now);
            }
        }
        appointmentTable.setItems(null);
        appointmentTable.setItems(monthlyAppointments);
    }
    @FXML
    void weeklyRadioButtonSelected(ActionEvent event){
        ObservableList<Appointments> weeklyAppointment = FXCollections.observableArrayList();
        for(Appointments A : appointmentList){

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(start.isAfter(now) && start.isBefore(now.plusHours(168))) {
                weeklyAppointment.add(A);
                System.out.println("Added");
                System.out.println(now);
            }
        }
        appointmentTable.setItems(null);
        appointmentTable.setItems(weeklyAppointment);
    }
    @FXML
    void allRadioButtonSelected(ActionEvent event){
        setTables();
    }

    public static void setLists(){
        customerList = null;
        appointmentList = null;
        customerList = DBCustomers.getAllCustomers();
        appointmentList = DBAppointments.getAllAppointments();

    }
    public void setTables(){
        appointmentTable.setItems(appointmentList);
        customerTable.setItems(customerList);
    }
    private static Customers selectedCustomer;
    private static Appointments selectedAppointment;
    public static Customers getSelectedCustomer(){
        return selectedCustomer;
    }
    public static Appointments getSelectedAppointment(){
        return selectedAppointment;
    }

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

