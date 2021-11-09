package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Appointments;
import com.example.schedulingapp.model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBAppointments {
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
                String[] appointmentStartDateTime = rs.getString("Start").split(" ");
                Date appointmentStartDate = Date.valueOf(appointmentStartDateTime[0]);
                Time appointmentStartTime = Time.valueOf(appointmentStartDateTime[1]);
                String[] appointmentEndDateTime = rs.getString("End").split(" ");
                Date appointmentEndDate = Date.valueOf(appointmentEndDateTime[0]);
                Time appointmentEndTime = Time.valueOf(appointmentEndDateTime[1]);
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments A = new Appointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation,
                        appointmentType, appointmentStartDate, appointmentStartTime, appointmentEndDate, appointmentEndTime,
                customerId, userId, contactId);
                alist.add(A);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return alist;
    }
}
