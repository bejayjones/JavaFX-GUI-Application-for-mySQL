package com.example.schedulingapp.View;

import com.example.schedulingapp.DBAccess.DBContacts;
import com.example.schedulingapp.DBAccess.DBCustomers;
import com.example.schedulingapp.DBAccess.DBUsers;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Appointments;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class updateAppointmentController implements Initializable {

    /**
     * combobox that holds the type options
     */
    @FXML
    private ComboBox<String> typeComboBox;
    /**
     * textfield used to collect appointment description
     */
    @FXML
    private TextField descriptionTextField;
    /**
     * datepicker widget that collects the date for an added appointment
     */
    @FXML
    private DatePicker dateWidget;
    /**
     * combo box that allows a user to select and end time
     */
    @FXML
    private ComboBox<String> endHourCombo;
    /**
     * combo box that allows a user to select a start time
     */
    @FXML
    private ComboBox<String> startHourCombo;
    /**
     * combo box that lets a user select a customer id
     */
    @FXML
    private ComboBox<Integer> customerIdComboBox;
    /**
     * combo box that lets a user select a contact id
     */
    @FXML
    private ComboBox<Integer> contactIdComboBox;
    /**
     * combo box that lets a user select a user id
     */
    @FXML
    private ComboBox<Integer> userIdComboBox;

    @FXML
    private TextField idTextField;

    private ObservableList<String> startTimeListEST = FXCollections.observableArrayList();
    private ObservableList<String> endTimeListEST = FXCollections.observableArrayList();
    private ObservableList<String> startTimeListLocal = FXCollections.observableArrayList();
    private ObservableList<String> endTimeListLocal = FXCollections.observableArrayList();
    private ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> contactIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> userIdList = FXCollections.observableArrayList();

    /**
     * checks for input validation, then updates the selected appointment in the mysql database
     * @param event
     * @throws ParseException
     *
     */
    @FXML
    void saveButtonClicked(ActionEvent event) throws ParseException {
        ObservableList<Appointments> apptList = homeController.getAppointmentList();
        boolean doubleBooked = false;
        String appointmentId = idTextField.getText();
        String title = "title";
        String appointmentType = typeComboBox.getValue();
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

        for (Appointments A : apptList) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime aStartTime = LocalDateTime.parse(A.getAppointmentStartDate(), dateTimeFormatter);
            LocalDateTime aEndTime = LocalDateTime.parse(A.getAppointmentEndDate(), dateTimeFormatter);
            LocalDateTime startTime = LocalDateTime.parse(startDate, dateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(endDate, dateTimeFormatter);
            if ((aStartTime.isBefore(startTime) && (aEndTime.isBefore(startTime)) || (aStartTime.isAfter(endTime))) || appointmentId.equals(String.valueOf(A.getAppointmentId()))) {

            } else {
                doubleBooked = true;
            }
        }
        if (doubleBooked) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Double Booked");
            alert.setHeaderText("Customer already has an appointment at this time");
            alert.show();
        } else {
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
                ps.executeUpdate();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                goHome();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * returns the user back to the home view
     * @throws IOException
     */
    void goHome() throws IOException {
        Stage stage = new Stage();
        Locale userLocale = Locale.getDefault();
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
    /**
     * confirms that the user wants to cancel, then sends the user back to the home view
     * @param event
     */
    @FXML
    void closeButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel updating this Appointment?");
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
     * converts local time from the users selection to UTC
     * @param local
     * @return
     * @throws ParseException
     */
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
    /**
     * converts UTC time options into the users local time
     * @throws ParseException
     */
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

    /**
     * intializes the update appointment view by setting the combo boxes as well as populating the
     * text fields and combo boxes and datepicker with the information gathered from the selected appointments
     * @param url
     * @param resourceBundle
     */
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
        typeComboBox.setValue(homeController.getSelectedAppointment().getAppointmentType());
        descriptionTextField.setText(homeController.getSelectedAppointment().getAppointmentDescription());
        String startDateTime[] = homeController.getSelectedAppointment().getAppointmentStartDate().split(" ");
        String endDateTime[] = homeController.getSelectedAppointment().getAppointmentEndDate().split(" ");
        dateWidget.setValue(LocalDate.parse(startDateTime[0]));
        startHourCombo.setValue(startDateTime[1]);
        endHourCombo.setValue(endDateTime[1]);
        customerIdComboBox.setValue(homeController.getSelectedAppointment().getCustomerId());
        contactIdComboBox.setValue(homeController.getSelectedAppointment().getContactId());
        userIdComboBox.setValue(homeController.getSelectedAppointment().getUserId());
        ObservableList<String> typeItems = FXCollections.observableArrayList();
        typeItems.addAll("Planning Session", "De-Briefing", "Miscellaneous");
        typeComboBox.setItems(typeItems);
    }
}
