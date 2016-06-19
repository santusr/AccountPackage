/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.narration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAONarration {
     //set Table
    private static final String TABAL = "narration";
    
    static void save(OBJNarration obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (Narrationcode, Narration) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET Narration = ? WHERE Narrationcode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());

            st.executeUpdate();
        }
    }

    static OBJNarration getNavi(Connection conn, int Ix) throws SQLException {

        OBJNarration obj = null;
        // SQL Server
        // String sql = "SELECT TOP("+Ix+") * FROM "+TABAL+" ORDER BY Narrationcode ASC";
       
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY Narrationcode ASC LIMIT"+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJNarration(
                    result.getString("Narrationcode"),
                    result.getString("Narration"));
        }

        return obj;
    }

    static OBJNarration serch(Connection conn, String code) throws SQLException {

        OBJNarration obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE Narrationcode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJNarration(
                    result.getString("Narrationcode"),
                    result.getString("Narration"));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT (*) AS val FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            // SQL Server
            // index++;
            
            //MySql
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE Narrationcode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE Narrationcode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT Narrationcode FROM "+TABAL+" ORDER BY Narrationcode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("Narrationcode");
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
