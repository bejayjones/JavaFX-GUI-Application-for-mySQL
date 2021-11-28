package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Contacts;
import com.example.schedulingapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for fetching the contacts from the SQL database and storing their ID and Name into a
 * Contact object
 */
public class DBContacts {
    /**
     * This method uses a select statement and result set to store the contacts into a Contact object
     * @return
     */
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contacts C = new Contacts(contactId, contactName);
                clist.add(C);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
