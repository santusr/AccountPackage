/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainApp.findwindow;

import core.DBConnection;
import core.Exp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class SERFindWindow {

    static ArrayList<OBJFindWindow> getData(String table, String code, String name) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getData(conn, table, code, name);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getDataWithCustomeQuery(String table, String code, String name, String customeWhere) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getDataWithCustomeQuery(conn, table, code, name, customeWhere);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getData(String table1, String table2, String code, String name, String key, String status) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getData(conn, table1, table2, code, name, key, status);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getData(String table, String code, String name, String s) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getData(conn, table, code, name, s);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getData(String table, String code, String name, String s, String ss) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getData(conn, table, code, name, s, ss);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getLikeData(String table, String code, String name, String s, String ss) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getLikeData(conn, table, code, name, s, ss);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getLikeData(String table, String code, String name, String ty, String s, String ss) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getLikeData(conn, table, code, name, ty, s, ss);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
    
    static ArrayList<OBJFindWindow> getLikeData(String table1, String table2, String code, String name, String key, String status, String s, String ss) {
        ArrayList<OBJFindWindow> data = null;
        try {
            Connection conn = DBConnection.getConnection();
            data = DAOFindWindow.getLikeData(conn, table1, table2, code, name, key, status, s, ss);
            conn.close();
        } catch (SQLException ex) {
            Exp.Handle(ex);
        }

        return data; 
    }
}
