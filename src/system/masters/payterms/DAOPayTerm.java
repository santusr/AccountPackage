/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.payterms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOPayTerm {
  //set Table
    private static final String TABAL = "PayTerms";
    
    static void save(OBJPayTerm obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" ( PTCode, Description) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET Description = ? WHERE PTCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());

            st.executeUpdate();
        }
    }

    static OBJPayTerm getNavi(Connection conn, int Ix) throws SQLException {

        OBJPayTerm obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+ Ix +") * FROM "+TABAL+" ORDER BY PTCode ASC";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY PTCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPayTerm(
                    result.getString("PTCode"),
                    result.getString("Description"));
        }

        return obj;
    }

    static OBJPayTerm serch(Connection conn, String code) throws SQLException {

        OBJPayTerm obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE PTCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJPayTerm(
                    result.getString("PTCode"),
                    result.getString("Description"));
        }

        return obj;
    }

    static int getValue(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM "+TABAL+"";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        //SQL Server
        //PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE PTCode = '" + code + "'");
        String sql = "UPDATE " + TABAL + " SET Status = ? WHERE PTCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
        String ID = "01";
        String sql = "SELECT PTCode FROM "+TABAL+" ORDER BY PTCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("PTCode");
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
