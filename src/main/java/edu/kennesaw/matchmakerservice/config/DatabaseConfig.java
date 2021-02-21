package edu.kennesaw.matchmakerservice.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public DatabaseConfig(){}

    public static final String MYSQL_DRIVER_CLASS_NAME= "com.mysql.jdbc.Driver";
    public static final String USERNAME = "gamer";
    public static final String PASSWORD = "gamer123";
    public static final String MYSQL_DB = "jdbc:mysql://mysql-19988-0.cloudclusters.net:19988/MatchMaker?useSSL=false";

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
