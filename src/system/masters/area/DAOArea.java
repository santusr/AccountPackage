/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.masters.area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class DAOArea {
    
    static void save(OBJArea area, Connection conn, int Act) throws SQLException {
        if (Act == 1) {
            String sql = "INSERT INTO Area (Areacode, Description, ShortDesc, City, Country) VALUES (?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, area.getCode());
            st.setString(2, area.getName());
            st.setString(3, area.getDesc());
            st.setString(4, area.getCity());
            st.setString(5, area.getCountry());
            st.execute();
            
        } else {
            String sql = "UPDATE Area SET Description = ?, ShortDesc = ?, City = ?, Country = ? WHERE AreaCode = '" + area.getCode() + "'";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, area.getName());
            st.setString(2, area.getDesc());
            st.setString(3, area.getCity());
            st.setString(4, area.getCountry());

            st.executeUpdate();
        }
    }

    static OBJArea getArea(Connection conn, int Ix) throws SQLException {

        OBJArea area = null;
        //SQL Server
        //String sql = "SELECT TOP ("+Ix+") * FROM Area ORDER BY AreaCode ASC";
        
        //MySql
        //Ix =-1;
        String sql = "SELECT * FROM Area ORDER BY AreaCode ASC LIMIT "+Ix+",1";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            area = new OBJArea(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5));
        }

        return area;
    }

    static OBJArea serchArea(Connection conn, String code) throws SQLException {

        OBJArea area = null;
        String sql = "SELECT * FROM Area WHERE AreaCode = '" + code + "' AND Status = '0'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {

            area = new OBJArea(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5));
        }

        return area;
    }

    static int getArea(Connection conn) throws SQLException {

        int index = 0;
        String sql = "SELECT COUNT(*) AS val FROM Area";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            //index++;
            index = result.getInt("val");
        }
        return index;
    }

    static void deleteArea(Connection conn, String code) throws SQLException {
        //PreparedStatement st = conn.prepareStatement("DELETE FROM Area WHERE AreaCode = '" + code + "'");
        String sql = "UPDATE Area SET Status = ? WHERE AreaCode = '" + code + "'";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, "1");
        st.execute();
        st.close();
    }

    static String genID(Connection conn) throws SQLException {
         String ID = "001";
        String sql = "SELECT AreaCode FROM Area ORDER BY AreaCode";

        PreparedStatement st = conn.prepareStatement(sql);
        st.execute();
        ResultSet result = st.getResultSet();
        while (result.next()) {
            String cd = result.getString("AreaCode");
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
