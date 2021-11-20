package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBReports {
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
