/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.costcenter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOCostCenter {
    //set Table
    private static final String TABAL = "CostCenter";
    
    static void save(OBJCostCenter obj, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO "+TABAL+" (CostCode, Description, working_date) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getCode());
            st.setString(2, obj.getName());
            st.setString(3, obj.getWorkingDate());
            st.execute();
            
        } else {
            String sql = "UPDATE "+TABAL+" SET Description = ?, working_date = ? WHERE CostCode = '" + obj.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getWorkingDate());

            st.executeUpdate();
        }
    }

    static OBJCostCenter getNavi(Connection conn, int Ix) throws SQLException {

        OBJCostCenter obj = null;
        //SQL Server
        //String sql = "SELECT TOP("+Ix+") * FROM "+TABAL+" ORDER BY CostCode";
        
        //My Sql
        //Ix =-1;
        String sql = "SELECT * FROM "+TABAL+" ORDER BY CostCode LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCostCenter(
                    result.getString(1),
                    result.getString(2),
                    result.getString(4));
        }

        return obj;
    }

    static OBJCostCenter serch(Connection conn, String code) throws SQLException {

        OBJCostCenter obj = null;
        String sql = "SELECT * FROM "+TABAL+" WHERE CostCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            obj = new OBJCostCenter(
                    result.getString(1),
                    result.getString(2),
                    result.getString(4));
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
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void delete(Connection conn, String code) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM "+TABAL+" WHERE CostCode = '" + code + "'");
        st.execute();
        st.close();
    }
    
    static String genID(Connection conn) throws SQLException {
        String ID = "001";
        String sql = "SELECT CostCode FROM "+TABAL+" ORDER BY CostCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("CostCode");
            int i = Integer.parseInt(cd);
            i++;
            cd = i+"";
            i = cd.length();
            for (int j = i; j < 3; j++) {
                cd = "0"+cd;
            }
            ID = cd;
        }
        return ID;
    }
}
