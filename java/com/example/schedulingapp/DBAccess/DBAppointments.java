package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Appointments;
import com.example.schedulingapp.model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is responsible for retrieving result sets related to the appointment table in the mysql database
 */
public class DBAppointments {
    /**
     * This method uses a select staetment and a result set to get the entire list of appointments from the SQL database
     * @return a list of appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                String startDateUTC = rs.getString("Start");
                String endDateUTC = rs.getString("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime appointmentStartDT = LocalDateTime.parse(startDateUTC, dateTimeFormatter);
                LocalDateTime appointmentEndDT = LocalDateTime.parse(endDateUTC, dateTimeFormatter);
                String appointmentStartUTC = appointmentStartDT.toString().replace('T', ' ');
                String appointmentEndUTC = appointmentEndDT.toString().replace('T', ' ');
                String appointmentStart = "";
                String appointmentEnd = "";
                try {

                    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date utcTime = utcFormat.parse(appointmentStartUTC);

                    DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    localFormat.setTimeZone(TimeZone.getDefault());
                    appointmentStart = localFormat.format(utcTime);

                    utcTime = utcFormat.parse(appointmentEndUTC);
                    appointmentEnd = localFormat.format(utcTime);

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                Appointments A = new Appointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation,
                        appointmentType, appointmentStart, appointmentEnd,
                customerId, userId, contactId);
                alist.add(A);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return alist;
    }

    /**
     * this class uses a select statement to create a result set from the appointments table to fetch appointments
     * based on the contact ID passed as a parameter
     * @param contact
     * @return observable list that contains a list of appointments
     */
    public static ObservableList<Appointments> getAppointmentsByContact(int contact) {

        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments WHERE CONTACT_ID = ?";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ps.setInt(1, contact);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                String startDateUTC = rs.getString("Start");
                String endDateUTC = rs.getString("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime appointmentStartDT = LocalDateTime.parse(startDateUTC, dateTimeFormatter);
                LocalDateTime appointmentEndDT = LocalDateTime.parse(endDateUTC, dateTimeFormatter);
                String appointmentStartUTC = appointmentStartDT.toString().replace('T', ' ');
                String appointmentEndUTC = appointmentEndDT.toString().replace('T', ' ');
                String appointmentStart = "";
                String appointmentEnd = "";
                try {
                    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date utcTime = utcFormat.parse(appointmentStartUTC);

                    DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    localFormat.setTimeZone(TimeZone.getDefault());
                    appointmentStart = localFormat.format(utcTime);

                    utcTime = utcFormat.parse(appointmentEndUTC);
                    appointmentEnd = localFormat.format(utcTime);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                Appointments A = new Appointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation,
                        appointmentType, appointmentStart, appointmentEnd,
                        customerId, userId, contactId);
                alist.add(A);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return alist;
    }
}
