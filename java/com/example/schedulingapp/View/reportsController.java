package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBAppointments;
import com.example.schedulingapp.DBAccess.DBContacts;
import com.example.schedulingapp.DBAccess.DBReports;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Appointments;
import com.example.schedulingapp.model.Contacts;
import com.example.schedulingapp.model.Countries;
import com.example.schedulingapp.model.Reports;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML
    private TableView<Reports> report1;

    @FXML
    private TableColumn<Reports, Integer> monthColumn;

    @FXML
    private TableColumn<Reports, Integer> yearColumn;

    @FXML
    private TableColumn<Reports, String> typeColumn;

    @FXML
    private TableColumn<Reports, Integer> totalColumn;

    @FXML
    private TableView<Appointments> report2;

    @FXML
    private TableColumn<Appointments, Integer> apptId;

    @FXML
    private TableColumn<Appointments, String> title;

    @FXML
    private TableColumn<Appointments, String> type;

    @FXML
    private TableColumn<Appointments, String> description;

    @FXML
    private TableColumn<Appointments, String> start;

    @FXML
    private TableColumn<Appointments, String> end;

    @FXML
    private TableColumn<Appointments, Integer> customerId;

    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private TableView<Reports> yearTable;

    @FXML
    private TableColumn<Reports, Integer> secondYearColumn;

    @FXML
    private TableColumn<Reports, Integer> totalYearColumn;

    public void setContactCombo(){
        for(Contacts C : contacts){
            contactNames.add(C.getContactName());
        }
        contactCombo.setItems(contactNames);
    }

    @FXML
    void contactComboSelected(){
        String selectedContact = contactCombo.getValue();
        for(Contacts C : contacts){
            if(selectedContact.equals(C.getContactName())){
                contactReport = DBAppointments.getAppointmentsByContact(C.getContactId());
                report2.setItems(contactReport);
            }
        }
    }

    ObservableList<Reports> monthlyTypeReport = DBReports.getMonthlyTypeReport();
    ObservableList<Reports> yearlyReport = DBReports.getYearlyReport();
    ObservableList<Appointments> contactReport = FXCollections.observableArrayList();
    ObservableList<Contacts> contacts = DBContacts.getAllContacts();
    ObservableList<String> contactNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        type.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        description.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        start.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        end.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        secondYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        totalYearColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        setContactCombo();
        //contactReport = DBAppointments.getAppointmentsByContact(1);
        report1.setItems(monthlyTypeReport);
        yearTable.setItems(yearlyReport);
        report2.setItems(contactReport);
    }
}
