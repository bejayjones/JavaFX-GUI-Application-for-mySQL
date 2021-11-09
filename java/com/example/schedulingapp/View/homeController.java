package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBAppointments;
import com.example.schedulingapp.DBAccess.DBCustomers;
import com.example.schedulingapp.model.Appointments;
import com.example.schedulingapp.model.Customers;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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
    private TableColumn<Appointments, Date> apptDate;

    @FXML
    private TableColumn<Appointments, Date> apptStart;

    @FXML
    private TableColumn<Appointments, Date> apptEnd;

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

    private ObservableList customerList = FXCollections.observableArrayList();
    private ObservableList appointmentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerList = DBCustomers.getAllCustomers();
        customerTable.setItems(customerList);

        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptcustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        appointmentList = DBAppointments.getAllAppointments();
        appointmentTable.setItems(appointmentList);
    }
}

