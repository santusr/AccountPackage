/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.inventory.master.unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOUnit {
  //set Table
    private static final String TABAL = "UOM";
    
    static void save(OBJUnit obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( UOMCode, Description) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET Description = ? WHERE UOMCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());

            st.executeUpdate();
        }
    }

    static OBJUnit getNavi(Connection conn, int Ix) throws SQLException {

        OBJUnit obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY UOMCode ASC";
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY ItemCode ASC";
        
        //MySql
        //Ix -= 1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY WHERE Status = '0' UOMCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJUnit(
                    result.getString("UOMCode"),
                    result.getString("Description"));
        }

        return obj;
    }

    static OBJUnit serch(Connection conn, String code) throws SQLException {

        OBJUnit obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE UOMCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJUnit(
                    result.getString("UOMCode"),
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
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE UOMCode = '" + code + "'");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT UOMCode FROM "+TABAL+" ORDER BY UOMCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("UOMCode");
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
