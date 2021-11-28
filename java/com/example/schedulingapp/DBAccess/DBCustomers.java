package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class is responsible for taking information from the SQL database and passing it into a customer object
 */
public class DBCustomers {
    /**
     * This method uses a select statement and result set to take information from the sql database and passes
     * it into a customer object
     * @return a list of customer objects
     */
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerAddress = rs.getString("Address");
                String customerPhone = rs.getString("Phone");
                int customerDivisionId = rs.getInt("Division_ID");
                Customers C = new Customers(customerId, customerName, customerPostalCode, customerAddress,
                        customerPhone, customerDivisionId);
                clist.add(C);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}