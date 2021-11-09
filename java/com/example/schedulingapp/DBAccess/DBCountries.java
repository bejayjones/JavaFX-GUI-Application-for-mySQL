package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class DBCountries {

    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                clist.add(C);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}


