package com.example.schedulingapp.Database;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Responsible for opening and fetching the connection to the mySQL Database
 */
public class DBUtil {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?ConnectionTimeZone = SERVER"; //LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; //Driver reference
    private static final String userName = "sqlUser"; // sqlUser
    private static final String password = "Passw0rd!"; // Password Passw0rd!
    /**
     * An instance of the connection class that will be set and retrieved throughout the project
     */
    public static Connection connection; // Connection interface
    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection established in open connection
     * @return connection
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the connection opened by open connection
     */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

