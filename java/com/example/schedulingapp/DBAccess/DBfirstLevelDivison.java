package com.example.schedulingapp.DBAccess;

import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.firstLevelDivision;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBfirstLevelDivison {

    public static ObservableList<firstLevelDivision> getAllFirstLevelDivision() {

        ObservableList<firstLevelDivision> flist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                firstLevelDivision F = new firstLevelDivision(divisionId, divisionName, countryId);
                flist.add(F);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flist;
    }
    public static ObservableList<String> getStates(){
        ObservableList<String> stateList = FXCollections.observableArrayList();
        ObservableList<firstLevelDivision> all = getAllFirstLevelDivision();
        for(firstLevelDivision state : all){
            if(state.getCountryId() == 1){
                stateList.add(state.getDivisionName());
            }
        }
        return stateList;
    }
    public static ObservableList<String> getCanadaProvinces(){
        ObservableList<String> provinceList = FXCollections.observableArrayList();
        ObservableList<firstLevelDivision> all = getAllFirstLevelDivision();
        for(firstLevelDivision state : all){
            if(state.getCountryId() == 3){
                provinceList.add(state.getDivisionName());
            }

        }
        return provinceList;
    }
    public static ObservableList<String> getUKProvinces(){
        ObservableList<String> provinceList = FXCollections.observableArrayList();
        ObservableList<firstLevelDivision> all = getAllFirstLevelDivision();
        for(firstLevelDivision state : all){
            if(state.getCountryId() == 2){
                provinceList.add(state.getDivisionName());
            }
        }
        return provinceList;
    }
    public static ObservableList<String> getAllDivisions(){
        ObservableList<String> stateList = FXCollections.observableArrayList();
        ObservableList<firstLevelDivision> all = getAllFirstLevelDivision();
        for(firstLevelDivision state : all){
            stateList.add(state.getDivisionName());
        }
        return stateList;
    }
}
