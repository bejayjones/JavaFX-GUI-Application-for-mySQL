package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBCustomers;
import com.example.schedulingapp.model.Customers;
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
import java.util.ResourceBundle;

public class homeController implements Initializable{

    @FXML
    private TableView<Customers> customerTable;

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
    private TableColumn<?, ?> apptID;

    @FXML
    private TableColumn<?, ?> apptFirstName;

    @FXML
    private TableColumn<?, ?> apptLastName;

    @FXML
    private TableColumn<?, ?> apptTime;

    @FXML
    private TableColumn<?, ?> apptDate;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

        customerList = DBCustomers.getAllCustomers();
        customerTable.setItems(customerList);
    }
}

