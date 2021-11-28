package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class is responsible for taking information from the database to generate specific reports
 */
public class DBReports {
    /**
     * uses a select statement and result set to gather the start month and year and type and frequency of appointments
     * by month and type
     * @return a list of appointments
     */
    public static ObservableList<Reports> getMonthlyTypeReport() {

        ObservableList<Reports> rlist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT month(START), year(START), Type, count(*) as ct FROM client_schedule.appointments GROUP BY Type, month(START), year(START);";
            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int month = rs.getInt("month(START)");
                int year = rs.getInt("year(START)");
                String type = rs.getString("Type");
                int count = rs.getInt("ct");
                Reports R = new Reports(month, year, type, count);
                rlist.add(R);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rlist;
    }

    /**
     * uses a select statement and result set to gather the start year and frequency of appointments by start year
     * @return a list of appointments
     */
    public static ObservableList<Reports> getYearlyReport(){
        ObservableList<Reports> rlist = FXCollections.observableArrayList();
        try{
            String sql = "SELECT year(START), count(*) as ct FROM client_schedule.appointments GROUP BY 1";

            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int year = rs.getInt("year(START)");
                int count = rs.getInt("ct");
                Reports R = new Reports(year, count);
                rlist.add(R);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return rlist;
    }
}
