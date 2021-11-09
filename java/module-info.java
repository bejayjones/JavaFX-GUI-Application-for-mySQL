module com.example.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.example.schedulingapp to javafx.fxml;
    exports com.example.schedulingapp;
    exports com.example.schedulingapp.Database;
    opens com.example.schedulingapp.Database to javafx.fxml;
    exports com.example.schedulingapp.View;
    opens com.example.schedulingapp.View to javafx.fxml;
    opens com.example.schedulingapp.model to javafx.fxml;
    exports com.example.schedulingapp.model;


}