/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class DBConnection {

    private static Connection con;
    public static String db = "RAP";
    private static String uname = "root";
    private static String pass = "123";
    private static String host = "localhost";

    static {
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(new FileInputStream("data.key"));

            //get the property value and print it out
            db = prop.getProperty("database");
            uname = prop.getProperty("dbuser");
            pass = prop.getProperty("dbpassword");
            host = prop.getProperty("host");
            
        } catch (IOException ex) {
            Exp.Handle(ex);
        }
        try {
            System.out.println("DB is " + db);
            //MySql
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            //SQL Server
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();   
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public static synchronized Connection getConnection() throws SQLException {

        //MySql
        String url = "jdbc:mysql://" + host + ":3306/" + db + "?autoReconnect=true";
        con = DriverManager.getConnection(url, uname, pass);

        //SQL Servar
        //String url = "jdbc:sqlserver://"+host+":1433;databaseName="+db;
        //con = DriverManager.getConnection(url,uname,pass);


        return con;
    }
}
