package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class DBCustomers {

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