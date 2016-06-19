/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainApp.findwindow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class DAOFindWindow {

    static ArrayList<OBJFindWindow> getData(Connection conn, String table, String code, String name) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table+" WHERE Status = '0' ORDER BY "+code+"";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }

    static ArrayList<OBJFindWindow> getData(Connection conn, String table1, String table2, String code, String name, String key, String status) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table1+", "+table2+" WHERE "+key+" = '"+status+"' ORDER BY "+code+"";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }

    static ArrayList<OBJFindWindow> getData(Connection conn, String table, String code, String name, String s) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table+" WHERE Type = '"+s+"'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }

    static ArrayList<OBJFindWindow> getData(Connection conn, String table, String code, String name, String s, String ss) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table+" WHERE "+s+" = '"+ss+"'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }
    
    static ArrayList<OBJFindWindow> getLikeData(Connection conn, String table, String code, String name, String s, String ss) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table+" WHERE "+s+" LIKE '"+ss+"%'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }
    
    static ArrayList<OBJFindWindow> getLikeData(Connection conn, String table, String code, String name, String ty, String s, String ss) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table+" WHERE type = '"+ty+"' AND "+s+" LIKE '"+ss+"%'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }
    
    static ArrayList<OBJFindWindow> getLikeData(Connection conn, String table1, String table2, String code, String name, String key, String status, String s, String ss) throws SQLException {
        String sql = "SELECT "+code+", "+name+" FROM "+table1+", "+table2+" WHERE "+key+"='"+status+"' AND "+s+" LIKE '"+ss+"%'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();

        ArrayList<OBJFindWindow> Adata = new ArrayList<OBJFindWindow> ();
        while (result.next()) {
            OBJFindWindow data = new OBJFindWindow(
                    result.getString(code),
                    result.getString(name));
            
            Adata.add(data);
        }
        
        return Adata;
    }

}
