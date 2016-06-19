/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOStore {
  //set Table
    private static final String TABAL = "Store";
    
    static void save(OBJStore obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( StoreCode, Description, Name) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(3, obj.getName());
            st.setString(2, obj.getDesc());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET Name = ?, Description = ? WHERE StoreCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getDesc());

            st.executeUpdate();
        }
    }

    static OBJStore getNavi(Connection conn, int Ix) throws SQLException {

        OBJStore obj = null;
        // SQL Server
        // String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY StoreCode ASC";

        //MySql  
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" WHERE Status = '0' ORDER BY StoreCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJStore(
                    result.getString("StoreCode"),
                    result.getString("Name"),
                    result.getString("Description"));
        }

        return obj;
    }

    static OBJStore serch(Connection conn, String code) throws SQLException {

        OBJStore obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE StoreCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJStore(
                    result.getString("StoreCode"),
                    result.getString("Name"),
                    result.getString("Description"));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT * FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index++;
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE StoreCode = '" + code + "'");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT StoreCode FROM "+TABAL+" ORDER BY StoreCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("StoreCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 2; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
    }
}
