package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class is responsible for fetching the countries from the SQL database and storing their ID and Name into a
 * Countries object
 */
public class DBCountries {
    /**
     * this method uses a select statement and a result set to pass the information from the sql database into
     * a countries object
     * @return a list of country objects
     */
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


