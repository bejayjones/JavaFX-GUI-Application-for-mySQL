package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Countries;
import com.example.schedulingapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {
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
