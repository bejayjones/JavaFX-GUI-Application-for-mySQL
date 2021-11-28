package com.example.schedulingapp.DBAccess;
import com.example.schedulingapp.Database.DBUtil;
import com.example.schedulingapp.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for taking information from the database and turning into java objects
 */
public class DBfirstLevelDivison {
    /**
     * This method uses a select statement and a result set to fetch the all the firstleveldivision data from the database
     * and passes it into firstlevel division objects
     * @return a list of firstleveldivision objects
     */
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

    /**
     *
     * @return returns a list of states that belong to the country ID of 1
     */
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
    /**
     *
     * @return returns a list of states that belong to the country ID of 3
     */
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
    /**
     *
     * @return returns a list of states that belong to the country ID of 2
     */
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

    /**
     *
     * @return returns a the entire list of firstlevel divisions
     */
    public static ObservableList<String> getAllDivisions(){
        ObservableList<String> stateList = FXCollections.observableArrayList();
        ObservableList<firstLevelDivision> all = getAllFirstLevelDivision();
        for(firstLevelDivision state : all){
            stateList.add(state.getDivisionName());
        }
        return stateList;
    }
}
