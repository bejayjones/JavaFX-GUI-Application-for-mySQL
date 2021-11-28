package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Countries;
import com.example.schedulingapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for taking information from the database and passing it into a user object
 */
public class DBUsers {
    /**
     * this method uses a select statement and result set to gather an entire list of users from the database
     * and pass them into user objects
     * @return a list of user objects
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> ulist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int userId = rs.getInt("User_ID");
                Users U = new Users(userId);
                ulist.add(U);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ulist;
    }
}
