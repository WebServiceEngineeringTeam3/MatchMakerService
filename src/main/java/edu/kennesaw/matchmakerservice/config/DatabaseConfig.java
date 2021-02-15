package edu.kennesaw.matchmakerservice.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public DatabaseConfig(){}

    //TODO: NEED TO UPDATE CREDENTIALS DEPENDING ON ENVIRONMENT
    public static final String MYSQL_DRIVER_CLASS_NAME= "com.mysql.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "cisco2017";
    public static final String MYSQL_DB = "jdbc:mysql://localhost:3306/rms?useSSL=false";

    public Connection getConnection(){
        Connection conn = null;

        try {
            Class.forName(MYSQL_DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(MYSQL_DB, USERNAME, PASSWORD);
        }
        catch (SQLException e) {e.printStackTrace();}
        catch(ClassNotFoundException e){e.printStackTrace();}
        return conn;
    }

}
