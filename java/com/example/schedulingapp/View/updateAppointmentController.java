package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBContacts;
import com.example.schedulingapp.DBAccess.DBCustomers;
import com.example.schedulingapp.DBAccess.DBUsers;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Contacts;
import com.example.schedulingapp.model.Customers;
import com.example.schedulingapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class updateAppointmentController implements Initializable {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private DatePicker dateWidget;

    @FXML
    private ComboBox<String> endHourCombo;

    @FXML
    private ComboBox<String> startHourCombo;

    @FXML
    private ComboBox<Integer> customerIdComboBox;

    @FXML
    private ComboBox<Integer> contactIdComboBox;

    @FXML
    private ComboBox<Integer> userIdComboBox;

    private ObservableList<String> startTimeListEST = FXCollections.observableArrayList();
    private ObservableList<String> endTimeListEST = FXCollections.observableArrayList();
    private ObservableList<String> startTimeListLocal = FXCollections.observableArrayList();
    private ObservableList<String> endTimeListLocal = FXCollections.observableArrayList();
    private ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> contactIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> userIdList = FXCollections.observableArrayList();

    @FXML
    void saveButtonClicked(ActionEvent event) throws ParseException {
        String appointmentId = idTextField.getText();
        String title = "title";
        String appointmentType = typeTextField.getText();
        String appointmentDescription = descriptionTextField.getText();
        String appointmentLocation = "location";
        String appointmentDate = dateWidget.getValue().toString();
        String appointmentStart = startHourCombo.getValue();
        String appointmentEnd = endHourCombo.getValue();
        int customerId = customerIdComboBox.getValue();
        int userId = userIdComboBox.getValue();
        int contactId = contactIdComboBox.getValue();
        String startDate = appointmentDate + " " + appointmentStart;
        String endDate = appointmentDate + " " + appointmentEnd;
        String startDateUTC = localToUTC(startDate);
        String endDateUTC = localToUTC(endDate);


        try {
            PreparedStatement ps = DBUtil.getConnection().prepareStatement("UPDATE client_schedule.appointments" +
                    " SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?" +
                    " WHERE Appointment_ID = ?");
            ps.setString(1, title);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setString(5, startDateUTC);
            ps.setString(6, endDateUTC);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setString(10, appointmentId);
            System.out.println(ps);
            ps.executeUpdate();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            goHome();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    void goHome() throws IOException {
        Stage stage = new Stage();
        Locale userLocale = Locale.getDefault();
        Locale localeEN = new Locale("en_us");
        Locale localeFR = new Locale("fr_fr");

        ResourceBundle bundle = ResourceBundle.getBundle("com.example.schedulingapp.home", userLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("home.fxml"), bundle);
        Scene scene = new Scene(root);
        System.out.println(userLocale.getLanguage());
        if (userLocale.getLanguage().equals("en")) {
            stage.setTitle("Customer / Appointment");
        } else if (userLocale.getLanguage().equals("fr")) {
            stage.setTitle("Client / Rendevouz");
        }
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void closeButtonClicked(ActionEvent event) throws IOException {
        goHome();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    String localToUTC(String local) throws ParseException {
        String utc = "";

        DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        localFormat.setTimeZone(TimeZone.getDefault());
        Date localTime = localFormat.parse(local);

        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        utc = utcFormat.format(localTime);


        return utc;
    }

    void convertListTime() throws ParseException {
        try {
            for (String time : startTimeListEST) {
                DateFormat estFormat = new SimpleDateFormat("HH:mm");
                estFormat.setTimeZone(TimeZone.getTimeZone("EST"));
                Date estTime = estFormat.parse(time);
                DateFormat localFormat = new SimpleDateFormat("HH:mm");
                localFormat.setTimeZone(TimeZone.getDefault());
                startTimeListLocal.add(localFormat.format(estTime));
            }
            for (String time : endTimeListEST) {
                DateFormat estFormat = new SimpleDateFormat("HH:mm");
                estFormat.setTimeZone(TimeZone.getTimeZone("EST"));
                Date estTime = estFormat.parse(time);
                DateFormat localFormat = new SimpleDateFormat("HH:mm");
                localFormat.setTimeZone(TimeZone.getDefault());
                endTimeListLocal.add(localFormat.format(estTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTimeListEST.addAll(
                "08:00", "08:15", "08:30", "08:45",
                "09:00", "09:15", "09:30", "09:45",
                "10:00", "10:15", "10:30", "10:45",
                "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45",
                "13:00", "13:15", "13:30", "13:45",
                "14:00", "14:15", "14:30", "14:45",
                "15:00", "15:15", "15:30", "15:45",
                "16:00", "16:15", "16:30", "16:45",
                "17:00", "17:15", "17:30", "17:45",
                "18:00", "18:15", "18:30", "18:45",
                "19:00", "19:15", "19:30", "19:45",
                "20:00", "20:15", "20:30", "20:45",
                "21:00", "21:15", "21:30", "21:45");
        endTimeListEST.addAll(
                "08:15", "08:30", "08:45",
                "09:00", "09:15", "09:30", "09:45",
                "10:00", "10:15", "10:30", "10:45",
                "11:00", "11:15", "11:30", "11:45",
                "12:00", "12:15", "12:30", "12:45",
                "13:00", "13:15", "13:30", "13:45",
                "14:00", "14:15", "14:30", "14:45",
                "15:00", "15:15", "15:30", "15:45",
                "16:00", "16:15", "16:30", "16:45",
                "17:00", "17:15", "17:30", "17:45",
                "18:00", "18:15", "18:30", "18:45",
                "19:00", "19:15", "19:30", "19:45",
                "20:00", "20:15", "20:30", "20:45",
                "21:00", "21:15", "21:30", "21:45",
                "22:00"
        );
        try {
            convertListTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startHourCombo.setItems(startTimeListLocal);
        endHourCombo.setItems(endTimeListLocal);
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        ObservableList<Contacts> contactList = DBContacts.getAllContacts();
        ObservableList<Users> userList = DBUsers.getAllUsers();

        for(Customers c : customerList){
            customerIdList.add(c.getCustomerId());
        }
        for(Contacts c : contactList){
            contactIdList.add(c.getContactId());
        }
        for(Users u : userList){
            userIdList.add(u.getUserId());
        }
        customerIdComboBox.setItems(customerIdList);
        contactIdComboBox.setItems(contactIdList);
        userIdComboBox.setItems(userIdList);
        idTextField.setText(String.valueOf(homeController.getSelectedAppointment().getAppointmentId()));
        typeTextField.setText(homeController.getSelectedAppointment().getAppointmentType());
        descriptionTextField.setText(homeController.getSelectedAppointment().getAppointmentDescription());
        String startDateTime[] = homeController.getSelectedAppointment().getAppointmentStartDate().split(" ");
        String endDateTime[] = homeController.getSelectedAppointment().getAppointmentEndDate().split(" ");
        dateWidget.setValue(LocalDate.parse(startDateTime[0]));
        startHourCombo.setValue(startDateTime[1]);
        endHourCombo.setValue(endDateTime[1]);
        customerIdComboBox.setValue(homeController.getSelectedAppointment().getCustomerId());
        contactIdComboBox.setValue(homeController.getSelectedAppointment().getContactId());
        userIdComboBox.setValue(homeController.getSelectedAppointment().getUserId());
    }
}
